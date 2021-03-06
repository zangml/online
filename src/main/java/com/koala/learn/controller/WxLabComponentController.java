package com.koala.learn.controller;

import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.ClassifierMapper;
import com.koala.learn.dao.LabMapper;
import com.koala.learn.entity.*;
import com.koala.learn.service.WxComponentService;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.treat.WxViewUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/wx/component/")
public class WxLabComponentController {



    @Autowired
    WxComponentService wxComponentService;

    @Autowired
    ClassifierMapper mClassifierMapper;

    @Autowired
    LabMapper mLabMapper;

    @Autowired
    Gson mGson;

    @Autowired
    JedisAdapter mJedisAdapter;

    private static final  Logger logger= LoggerFactory.getLogger(WxLabComponentController.class);

    @RequestMapping(value = "init_smote")
    @ResponseBody
    public ServerResponse<Map<String,Object>> getSmoteData(){
        Map<String,Object> map =new HashMap();
        String key = RedisKeyUtil.getInitSmote();
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            Gson gson=new Gson();
            EchatsOptions options=gson.fromJson(cache,EchatsOptions.class);
            map.put("ratio","0与1的样本数量比为8.56:1");
            map.put("option",options);
            return ServerResponse.createBySuccess(map);
        }
        try {
            Instances instances =new Instances(new FileReader(Const.DATA_FOR_SMOTE));
            EchatsOptions options =WxViewUtils.reslovePCA(instances,12);
            Gson gson=new Gson();
            String optionsCache=gson.toJson(options);
            mJedisAdapter.set(key,optionsCache);
            //String ratio =wxComponentService.getRatio0To1(instances);
            map.put("ratio","0与1的样本数量比为8.56:1");
            map.put("option",options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess(map);
    }

    @RequestMapping(value = "get_smote")
    @ResponseBody
    public ServerResponse<Map<String,Object>> handleSmote(@RequestParam(value = "k_neighbors",defaultValue = "5") Integer kNeighbors,
                                                     @RequestParam(value = "ratio",defaultValue = "500")Integer ratio){

        if(ratio==0){
            return ServerResponse.createByError();
        }
        Map<String,Object> map =new HashMap();

        String key=RedisKeyUtil.getSmoteKey(kNeighbors,ratio);
        String ratioKey="GET_SMOTE_RATIO:"+ratio;
        String cache=mJedisAdapter.get(key);
        String ratioCache=mJedisAdapter.get(ratioKey);
        EchatsOptions echatsOptions;
        String ratioResult;
        Gson gson=new Gson();
        try {
            if(cache!=null){
                echatsOptions=gson.fromJson(cache,EchatsOptions.class);
                ratioResult=ratioCache;
            }else{
                Instances instances = new Instances(new FileReader(Const.DATA_FOR_SMOTE));
                instances.setClassIndex(instances.numAttributes() - 1);
                SMOTE smote =new SMOTE();
                String[] options = {"-S", String.valueOf(1), "-P", String.valueOf(ratio), "-K", String.valueOf(kNeighbors)};
                smote.setInputFormat(instances);
                smote.setOptions(options);
                Instances instances1 = Filter.useFilter(instances, smote);
                echatsOptions = WxViewUtils.reslovePCA(instances1,12);
                String cacheOptions=gson.toJson(echatsOptions);
                mJedisAdapter.set(key,cacheOptions);
                ratioResult= wxComponentService.getRatio0To1(instances1);
                mJedisAdapter.set(ratioKey,ratioResult);
            }
            map.put("ratio",ratioResult);
//            map.put("ratio","0与1的样本数量比为"+8.56/(ratio/100)+":1");
            map.put("option",echatsOptions);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess(map);
    }


    @RequestMapping("get_oneclasssvm")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleOneClassSVM(@RequestParam(value = "nu",defaultValue = "0.03") Double nu) throws Exception {

        File input =new File(Const.DATA_FOR_OCSVM);

        File out=wxComponentService.handleOcSvm(nu,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options =WxViewUtils.reslovePCA(instances,1);

        return ServerResponse.createBySuccess(options);

    }

    @RequestMapping("get_isolation")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleIsoLationForest(@RequestParam(value = "contamination",defaultValue = "0.02") Double contamination) throws Exception {

        String key=RedisKeyUtil.getIsolationKey(contamination);

        String cache=mJedisAdapter.get(key);
        Gson gson=new Gson();

        if(cache!=null){
            EchatsOptions options=gson.fromJson(cache,EchatsOptions.class);
            return ServerResponse.createBySuccess(options);
        }

        File input =new File(Const.DATA_FOR_ISOLATIONFOREST);

        File out=wxComponentService.handleIsolation(contamination,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options =WxViewUtils.reslovePCA(instances,1);

        String cacheOptions=gson.toJson(options);

        mJedisAdapter.set(key,cacheOptions);

        return ServerResponse.createBySuccess(options);

    }

    @RequestMapping("clear_isolation")
    @ResponseBody
    public ServerResponse<EchatsOptions> clearExceptionInIsolation(@RequestParam(value = "contamination",defaultValue = "0.02") Double contamination) throws Exception {

        File input =new File(Const.DATA_FOR_ISOLATIONFOREST);

        File out = wxComponentService.handleIsolation(contamination,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options =WxViewUtils.reslovePCAForClearIsolation(instances,1);

        return ServerResponse.createBySuccess(options);

    }

    @RequestMapping("init_normalization")
    @ResponseBody
    public ServerResponse<EchatsOptions> initNormalization(){
        try {
            EchatsOptions options= WxViewUtils.resloveNormalization(new Instances(new FileReader(Const.DATA_FOR_NORMALIZATION)));
            options.setTitle(new EchatsOptions.TitleBean("归一化前数据分布","以每个特征的样本均值表示"));
            return ServerResponse.createBySuccess(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createByError();
    }

    @RequestMapping("get_normalization")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleNormalization() throws Exception {
        File input =new File(Const.DATA_FOR_NORMALIZATION);
        File out=wxComponentService.handleNormalization(input);
        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
        EchatsOptions options =WxViewUtils.resloveNormalization(instances);
        options.setTitle(new EchatsOptions.TitleBean("归一化后数据分布","以每个特征的样本均值表示"));
        return ServerResponse.createBySuccess(options);
    }


    @RequestMapping("get_fft")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleFFT(@RequestParam(value = "attribute",defaultValue = "current") String attribute) throws IOException {

        File input =new File(Const.DATA_FOR_FFT_WX);

        File out=wxComponentService.handleFFT(attribute,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options=WxViewUtils.resloveFFT(instances,1);
//        WxEchartsForFFT options=WxViewUtils.resloveFFTNew(instances,1);

        return ServerResponse.createBySuccess(options);
    }

    @RequestMapping("get_wave_feature")
    @ResponseBody
    public ServerResponse handleWavest(@RequestParam(value = "waveLayer",defaultValue = "3") Integer waveLayer,
                                       @RequestParam(value = "windowLength",defaultValue = "10") Integer windowLength) throws IOException {
        Map<String,Object> map =new HashMap<>();
        File input =new File(Const.DATA_FOR_WAVEST_WX);
        File out = wxComponentService.handleWavest(input,waveLayer,windowLength);
        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
        List<String> attributeList=new ArrayList<>();
        for (int i=0;i<instances.numAttributes()-1;i++){
            attributeList.add(instances.attribute(i).name());
        }
        logger.info(attributeList.toString());
        map.put("attributeList",attributeList);
        map.put("dataSize",instances.size());
        map.put("fileName",out.getName().replace("arff","xls"));
        return ServerResponse.createBySuccess(map);
    }


    @RequestMapping("init_time_feature")
    @ResponseBody
    public ServerResponse initTimeFeature() throws IOException {
        Map<String,Object> map =new HashMap();
        Instances instances =new Instances(new FileReader(new File(Const.DATA_FOR_TIMEFEATURE).getAbsolutePath()));
        List<String> attributeList=new ArrayList<>();
        for (int i=0;i<instances.numAttributes();i++){
            attributeList.add(instances.attribute(i).name());
        }
        logger.info(attributeList.toString());
        map.put("attributeList",attributeList);
        map.put("dataSize",instances.size());
        return ServerResponse.createBySuccess(map);
    }

    @RequestMapping("get_time_feature")
    @ResponseBody
    public ServerResponse handleTimeFeature(@RequestParam(value = "windowLength",defaultValue = "10")Integer windowLength,
                                            @RequestParam(value = "avg",defaultValue = "0")Integer avg,
                                            @RequestParam(value = "std",defaultValue = "0")Integer std,
                                            @RequestParam(value = "var",defaultValue = "0")Integer var,
                                            @RequestParam(value = "skew",defaultValue = "0")Integer skew,
                                            @RequestParam(value = "kur",defaultValue = "0")Integer kur,
                                            @RequestParam(value = "ptp",defaultValue = "0")Integer ptp) throws IOException {
        Map<String,Object> map =new HashMap();
        File input=new File(Const.DATA_FOR_TIMEFEATURE);
        File out=wxComponentService.handleTimeFeature(input,windowLength,avg,std,var,skew,kur,ptp);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
        List<String> attributeList=new ArrayList<>();
        for (int i=0;i<instances.numAttributes();i++){
            attributeList.add(instances.attribute(i).name());
        }
        logger.info(attributeList.toString());
        map.put("attributeList",attributeList);
        map.put("dataSize",instances.size());
        map.put("fileName",out.getName().replace("arff","xls"));
        return ServerResponse.createBySuccess(map);
    }


    @RequestMapping("get_pca")
    @ResponseBody
    public ServerResponse handlePCA(@RequestParam(value = "dimension") Integer dimension) throws IOException {
        Map<String,Object> resultMap = new HashMap<>();
        File input =new File(Const.DATA_FOR_PCA_WX);
        String res =wxComponentService.handlePCA(input,dimension);
        res=res.substring(1,res.length()-1);
        System.out.println(res);
        String [] strs=res.split(" ");
        double[] ratio= new double[dimension];
        for(int i=0,j=0;j<strs.length;i++,j++){
            if(strs[j].isEmpty()|| strs[j].equals(" ")){
                i--;
                continue;
            }
            ratio[i]= Double.parseDouble(strs[j]);
        }
//        for(double dou:ratio){
//            System.out.println(dou);
//        }
        String fileName=input.getName().replace(".csv","")+
                "dimension_"+dimension+ ".xls";

        resultMap.put("fileName",fileName);
        resultMap.put("ratio",ratio);
        return ServerResponse.createBySuccess(resultMap);
    }



    @RequestMapping("{labType}/{classifierId}/get_algorithm")
    @ResponseBody
    public ServerResponse<List<List<String>>> handleAlgorithm(@RequestParam Map<String,String> param,
                                          @PathVariable("labType") Integer labType,
                                          @PathVariable("classifierId") Integer classifierId) {
         return wxComponentService.getAlgorithm(param,classifierId,labType);
    }


}
