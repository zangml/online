package com.koala.learn.controller;

import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.dao.ClassifierMapper;
import com.koala.learn.entity.Algorithm;
import com.koala.learn.entity.Blog;
import com.koala.learn.entity.Classifier;
import com.koala.learn.entity.ClassifierParam;
import com.koala.learn.service.*;
import com.koala.learn.vo.FeatureVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import weka.core.Instances;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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




    @RequestMapping("get_list")
    public String getAlgoList(Model model){

        List<Algorithm> algorithmList=algorithmService.getAllAlgos();

        List<Algorithm> preList=new ArrayList<>();
        List<Algorithm> featureList=new ArrayList<>();
        List<Algorithm> classfierList=new ArrayList<>();
        List<Algorithm> regressionList=new ArrayList<>();

        for(Algorithm algorithm :algorithmList){
            if(algorithm.getType().equals(0)){
                preList.add(algorithm);
            }
            if(algorithm.getType().equals(2)){
                featureList.add(algorithm);
            }
            if(algorithm.getType().equals(1)){
                Classifier classifier=labService.getClassifierById(algorithm.getTypeId());
                if(classifier.getLabId().equals(1)){
                    classfierList.add(algorithm);
                }
                if(classifier.getLabId().equals(0)){
                    regressionList.add(algorithm);
                }
            }
        }
        model.addAttribute("preList",preList);
        model.addAttribute("featureList",featureList);
        model.addAttribute("classfierList",classfierList);
        model.addAttribute("regressionList",regressionList);
        return "views/algorithm/algo_list";
    }

    @RequestMapping("get_algo_detail/{id}")
    public String getAlgoDetail(@PathVariable("id")Integer id, Model model){

        Algorithm algorithm=algorithmService.getAlgoById(id);

        Blog blog = blogService.getBlogById(algorithm.getBlogId());
        int type=algorithm.getType();
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
    public ServerResponse<List<List<String>>> handleAlgorithm(@RequestParam Map<String,String> param,
                                                              @PathVariable("id") Integer classifierId){

        Classifier classifier=labService.getClassifierById(classifierId);
        return wxComponentService.getAlgorithm(param,classifierId,classifier.getLabId());
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
