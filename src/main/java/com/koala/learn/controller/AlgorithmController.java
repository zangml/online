package com.koala.learn.controller;

import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.ClassifierParamMapper;
import com.koala.learn.dao.MessageMapper;
import com.koala.learn.entity.*;
import com.koala.learn.service.*;
import com.koala.learn.vo.AlgoZyVo;
import com.koala.learn.vo.FeatureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import weka.core.Instances;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/algo/")
public class AlgorithmController {

    @Autowired
    AlgorithmService algorithmService;

    @Autowired
    LabDesignerService labDesignerService;

    @Autowired
    LabService labService;

    @Autowired
    WxComponentService wxComponentService;

    @Autowired
    BlogService blogService;

    @Autowired
    Gson gson;

    @Autowired
    ClassifierParamMapper mClassifierParamMapper;

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    LabLearnService labLearnService;

    @Autowired
    MessageMapper messageMapper;
    @RequestMapping("get_list")
    public String getAlgoList(Model model){

        List<Algorithm> algorithmList=algorithmService.getAllAlgos();

        List<Algorithm> preList=new ArrayList<>();
        List<Algorithm> featureList=new ArrayList<>();
        List<Algorithm> classfierList=new ArrayList<>();
        List<Algorithm> regressionList=new ArrayList<>();
        List<Algorithm> deepLearningList=new ArrayList<>();

        for(Algorithm algorithm :algorithmList){
            if(algorithm.getType().equals(0)){
                preList.add(algorithm);
            }
            if(algorithm.getType().equals(2)){
                featureList.add(algorithm);
            }
            if(algorithm.getType().equals(1)){
                Classifier classifier=labService.getClassifierById(algorithm.getTypeId());
                if(classifier.getLabId().equals(1)||classifier.getLabId().equals(3)){
                    classfierList.add(algorithm);
                }
                if(classifier.getLabId().equals(0)||classifier.getLabId().equals(4)){
                    regressionList.add(algorithm);
                }
            }
            if(algorithm.getType().equals(3)){
                deepLearningList.add(algorithm);
            }
        }
        model.addAttribute("preList",preList);
        model.addAttribute("featureList",featureList);
        model.addAttribute("classfierList",classfierList);
        model.addAttribute("regressionList",regressionList);
        model.addAttribute("deepLearningList",deepLearningList);
        return "views/algorithm/algo_list";
    }

    @RequestMapping("get_algo_detail/{id}")
    public String getAlgoDetail(@PathVariable("id")Integer id,
                                @RequestParam(value = "cacheKey",required = false)String cacheKey,
                                @RequestParam(value = "paramsKey",required = false)String paramsKey,
                                @RequestParam(value = "messageId",required = false)Integer messageId,
                                Model model){


        if(messageId!=null){
            Message message=messageMapper.selectByPrimaryKey(messageId);
            message.setHasRead(1);
            messageMapper.updateByPrimaryKeySelective(message);
        }
        Algorithm algorithm=algorithmService.getAlgoById(id);
        algorithm.setUsedCount(algorithm.getUsedCount()+1);
        algorithmService.updateByPrimaryKeySelective(algorithm);

        Blog blog = blogService.getBlogById(algorithm.getBlogId());
        int type=algorithm.getType();
        List<List<String>> res = new ArrayList<>();
        if(cacheKey!=null){
            String cache=jedisAdapter.get(cacheKey);
            if(cache!=null){
                Classifier classifier=labService.getClassifierById(algorithm.getTypeId());
                Integer labType=classifier.getLabId();
                if(labType==1 || labType==3){
                    Result result = gson.fromJson(cache,Result.class);
                    res.add(Arrays.asList("算法", "召回率", "准确率", "精确率", "F-Measure", "ROC-Area"));
                    List<String> resList = Arrays.asList(classifier.getName(),
                            result.getRecall() + "", result.getAccuracy() + "",
                            result.getPrecision() + "", result.getfMeasure() + "", result.getRocArea() + "");
                    res.add(resList);
                }else if(labType==0 || labType==4) {
                    res.add(Arrays.asList("算法", "可释方差值", "平均绝对误差", "均方根误差", "中值绝对误差", "R方值"));
                    RegResult regResult =gson.fromJson(cache,RegResult.class);
                    List<String> resList = Arrays.asList(classifier.getName(),
                            regResult.getVarianceScore() + "", regResult.getAbsoluteError() + "",
                            Math.sqrt(regResult.getSquaredError()) + "", regResult.getMedianSquaredError() + "", regResult.getR2Score() + "");
                    res.add(resList);
                }
            }
        }
        if(paramsKey!=null){
            String paramStr=jedisAdapter.get(paramsKey);
            model.addAttribute("paramStr",paramStr);
        }
        model.addAttribute("res",res);
        if(type==0 || type==2){
            FeatureVo featureVo=labDesignerService.selectFeatureVoById(algorithm.getTypeId());
            System.out.println(featureVo);
            model.addAttribute("vo",featureVo);
        }else if(type==1){
            Classifier classifier=labService.getClassifierById(algorithm.getTypeId());
            List<ClassifierParam> paramList = labService.getParamByClassifierId(classifier.getId());
            classifier.setParams(paramList);
            model.addAttribute("classifier",classifier);
        }
        model.addAttribute("algorithm",algorithm);
        model.addAttribute("blog",blog);
        return "views/algorithm/algo_detail";

    }

    @RequestMapping("get_algo_detail/zy/{typeId}")
    public String getZyAlgoDetail(@PathVariable("typeId")Integer typeId, Model model){
        List<Algorithm> algorithmList =algorithmService.getAlgoByTypeId(typeId);

        List<AlgoZyVo> algoZyVos =new ArrayList<>();
        for(Algorithm algorithm:algorithmList){
            AlgoZyVo algoZyVo=new AlgoZyVo();
            algoZyVo.setAlgoDesc(algorithm.getAlgoDesc());
            algoZyVo.setBlog(blogService.getBlogById(algorithm.getBlogId()));
            algoZyVo.setCata_desc(algorithm.getCata_desc());
            algoZyVo.setDataDesc(algorithm.getDataDesc());
            algoZyVo.setId(algorithm.getId());
            algoZyVo.setName(algorithm.getName());
            algoZyVo.setType(algorithm.getType());
            algoZyVo.setTypeId(algorithm.getTypeId());
            algoZyVo.setUseFor(algorithm.getUseFor());

            algoZyVos.add(algoZyVo);
        }

        model.addAttribute("algorithms",algoZyVos);
        return "views/algorithm/zy_algo_detail";
    }
    @RequestMapping("get_init_echart/{id}")
    @ResponseBody
    public String getInitFeatureEcharts(@PathVariable("id")Integer id) throws Exception {
        if(id.equals(Const.SMOTE_ID)){
            return algorithmService.initSmoteEchart();
        }
        if(id.equals(Const.ISOLATION_FOREST_ID)){
            return algorithmService.initIsolationForestEchart();
        }
        if(id.equals(Const.NORMALIZATION_ID)){
            return algorithmService.initNormalizationEchart();
        }
        return null;
    }

    @RequestMapping(value = "get_feature_echart/{id}",method = RequestMethod.POST)
    @ResponseBody
    public String getFeatureEcharts(@PathVariable("id")Integer id,@RequestParam final Map<String,Object> param) throws Exception {
        System.out.println(param.toString());
        if(id.equals(Const.SMOTE_ID)){
            System.out.println("smote ratio:"+param.get("-P"));
            return algorithmService.getSmoteEchart(param);
        }
        if(id.equals(Const.ISOLATION_FOREST_ID)){
            System.out.println("isoforest conta:"+param.get("-i"));
            return algorithmService.getIsoForestEchart(param);
        }
        if(id.equals(Const.NORMALIZATION_ID)){
            return algorithmService.getNormalizationEchart();
        }
        if(id.equals(Const.FFT_ID)){
            return algorithmService.getFFTEchart(param);
        }
        return null;
    }

    @RequestMapping("get_algo_result/{id}")
    @ResponseBody
    public ServerResponse handleAlgorithm(@RequestParam Map<String,String> param,
                                                              @PathVariable("id") Integer classifierId){

        Classifier classifier=labService.getClassifierById(classifierId);
        List<List<String>> res= wxComponentService.getAlgorithm(param,classifierId,classifier.getLabId()).getData();
        return ServerResponse.createBySuccess(res);
    }


    @RequestMapping("get_feature_result/{id}")
    @ResponseBody
    public ServerResponse handleTimeFeature(@PathVariable("id")Integer id,@RequestParam Map<String,String> param) throws IOException {
        Map<String,Object> map =new HashMap();
        if(id.equals(Const.TIME_FEATURE_ID)){
            int avg=param.get("-a").equalsIgnoreCase("true")? 1:0;
            int std=param.get("-s").equalsIgnoreCase("true")? 1:0;
            int var=param.get("-v").equalsIgnoreCase("true")? 1:0;
            int skew=param.get("-k").equalsIgnoreCase("true")? 1:0;
            int kur=param.get("-kur").equalsIgnoreCase("true")? 1:0;
            int ptp=param.get("-ptp").equalsIgnoreCase("true")? 1:0;
            int windowLength= Integer.parseInt(param.get("-t"));

            File input=new File(Const.DATA_FOR_TIMEFEATURE);
            File out=wxComponentService.handleTimeFeature(input,windowLength,avg,std,var,skew,kur,ptp);

            Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
            List<String> attributeList=new ArrayList<>();
            for (int i=0;i<instances.numAttributes();i++){
                attributeList.add(instances.attribute(i).name());
            }
            map.put("attributeList",attributeList);
            map.put("dataSize",instances.size());
            map.put("fileName",out.getName().replace("arff","xls"));
            return ServerResponse.createBySuccess(map);
        }
        if(id.equals(Const.WAV_FEATURE_ID)){
            int waveLayer=Integer.parseInt(param.get("-wl"));
            int windowLength=Integer.parseInt(param.get("-w"));
            File input =new File(Const.DATA_FOR_WAVEST_WX);
            File out = wxComponentService.handleWavest(input,waveLayer,windowLength);
            Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
            List<String> attributeList=new ArrayList<>();
            for (int i=0;i<instances.numAttributes()-1;i++){
                attributeList.add(instances.attribute(i).name());
            }
            map.put("attributeList",attributeList);
            map.put("dataSize",instances.size());
            map.put("fileName",out.getName().replace("arff","xls"));
            return ServerResponse.createBySuccess(map);
        }
        return ServerResponse.createByError();
    }

}
