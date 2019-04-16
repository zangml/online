package com.koala.learn.controller;


import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.dao.ClassifierMapper;
import com.koala.learn.dao.LabMapper;
import com.koala.learn.entity.*;
import com.koala.learn.service.WxComponentService;
import com.koala.learn.utils.treat.WxViewUtils;
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

    @RequestMapping(value = "init_smote")
    @ResponseBody
    public ServerResponse<EchatsOptions> getSmoteData(){
        EchatsOptions options =new EchatsOptions();
        try {
            options= WxViewUtils.reslovePCA(new Instances(new FileReader(Const.DATA_FOR_SMOTE)),12);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ServerResponse.createBySuccess(options);
    }

    @RequestMapping(value = "get_smote")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleSmote(@RequestParam(value = "k_neighbors",defaultValue = "5") Integer kNeighbors,
                                                     @RequestParam(value = "ratio",defaultValue = "500")Integer ratio){

        EchatsOptions echatsOptions =new EchatsOptions();

        try {
            Instances instances = new Instances(new FileReader(Const.DATA_FOR_SMOTE));
            instances.setClassIndex(instances.numAttributes() - 1);
            SMOTE smote =new SMOTE();
            String[] options = {"-S", String.valueOf(1), "-P", String.valueOf(ratio), "-K", String.valueOf(kNeighbors)};
            smote.setInputFormat(instances);
            smote.setOptions(options);
            Instances instances1 = Filter.useFilter(instances, smote);
            echatsOptions=WxViewUtils.reslovePCA(instances1,12);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess(echatsOptions);
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

        File input =new File(Const.DATA_FOR_ISOLATIONFOREST);

        File out=wxComponentService.handleIsolation(contamination,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options =WxViewUtils.reslovePCA(instances,1);

        return ServerResponse.createBySuccess(options);

    }

    @RequestMapping("init_normalization")
    @ResponseBody
    public ServerResponse<EchatsOptions> initNormalization(){
        EchatsOptions options =new EchatsOptions();
        try {
            options= WxViewUtils.resloveNormalization(new Instances(new FileReader(Const.DATA_FOR_NORMALIZATION)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess(options);
    }

    @RequestMapping("get_normalization")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleNormalization() throws Exception {
        File input =new File(Const.DATA_FOR_NORMALIZATION);
        File out=wxComponentService.handleNormalization(input);
        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
        EchatsOptions options =WxViewUtils.resloveNormalization(instances);
        return ServerResponse.createBySuccess(options);
    }


    @RequestMapping("get_fft")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleFFT(@RequestParam(value = "attribute",defaultValue = "current") String attribute) throws IOException {

        File input =new File(Const.DATA_FOR_FFT_WX);

        File out=wxComponentService.handleFFT(attribute,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options=WxViewUtils.resloveFFT(instances,1);

        return ServerResponse.createBySuccess(options);
    }



    @RequestMapping("{labType}/{classifierId}/get_algorithm")
    @ResponseBody
    public ServerResponse<List<List<String>>> handleAlgorithm(@RequestParam Map<String,String> param,
                                          @PathVariable("labType") Integer labType,
                                          @PathVariable("classifierId") Integer classifierId) {
         return wxComponentService.getAlgorithm(param,classifierId,labType);
    }




}
