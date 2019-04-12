package com.koala.learn.service;


import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.dao.ClassifierMapper;
import com.koala.learn.dao.ClassifierParamMapper;
import com.koala.learn.entity.Classifier;
import com.koala.learn.entity.ClassifierParam;
import com.koala.learn.entity.RegResult;
import com.koala.learn.entity.Result;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.WekaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class WxComponentService {

    @Autowired
    ClassifierParamMapper mClassifierParamMapper;

    @Autowired
    ClassifierMapper mClassifierMapper;

    @Autowired
    Gson mGson;

    @Autowired
    LabLearnService mLabLearnService;

    public static final Logger logger = LoggerFactory.getLogger(WxComponentService.class);

    public File handleOcSvm(double nu,File input) throws IOException {
        input = WekaUtils.arff2csv(input);

        File out=new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv", "") +"nu" +nu + ".csv");
        if(out.exists()){
            System.out.println("从已有文件中获取");
            out=WekaUtils.csv2arff(out);
            return out;
        }
        String ocsvmdec = "python "+ Const.OCSVM_FOR_WX+ " nu="+nu
                +" path="+input.getAbsolutePath()+" opath="+out;
        System.out.println(ocsvmdec);
        PythonUtils.execPy(ocsvmdec);
        out=WekaUtils.csv2arff(out);
        return out;
    }

    public File handleIsolation(double contamination,File input) throws IOException {
        input = WekaUtils.arff2csv(input);
        File out=new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv", "") +"con" +contamination+ ".csv");
        if(out.exists()){
            out=WekaUtils.csv2arff(out);
            return out;
        }
        String isoLationmdec = "python "+ Const.ISOLATIONFOREST_FOR_WX+ " contamination="+contamination
                +" path="+input.getAbsolutePath()+" opath="+out;
        System.out.println(isoLationmdec);
        PythonUtils.execPy(isoLationmdec);
        out=WekaUtils.csv2arff(out);
        return out;
    }

    public File handleFFT(String attribute,File input) throws IOException {
        input = WekaUtils.arff2csv(input);
        File out = new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv", "") +
                        "attribute_" + attribute + ".csv");
        if (out.exists()) {
            out = WekaUtils.csv2arff(out);
            return out;
        }
        String fftDesc = "python " + Const.FFT_FOR_WX + " attribute=" + attribute
                + " path=" + input.getAbsolutePath() + " opath=" + out;
        System.out.println(fftDesc);
        PythonUtils.execPy(fftDesc);
        out = WekaUtils.csv2arff(out);
        return out;
    }

    public ServerResponse<List<List<String>>> getAlgorithm(Map<String,String> param, Integer classifierId, Integer labType){
        Classifier classifier = mClassifierMapper.selectByPrimaryKey(classifierId);
        List<List<String>> res = new ArrayList<>();
        if (labType == 1) {
            res.add(Arrays.asList("算法", "召回率", "准确率", "精确率", "F-Measure", "ROC-Area"));
            Result result =getResult(param,classifierId,classifier);

            if (result == null) {
                return ServerResponse.createByErrorMessage("运算失败");
            }

            List<String> resList = Arrays.asList(classifier.getName(),
                    result.getRecall() + "", result.getAccuracy() + "",
                    result.getPrecision() + "", result.getfMeasure() + "", result.getRocArea() + "");
            res.add(resList);

        } else {

            res.add(Arrays.asList("算法", "可释方差值", "平均绝对误差", "均方根误差", "中值绝对误差", "R方值"));
            RegResult regResult =getRegResult(param,classifierId,classifier);

            if (regResult == null) {
                return ServerResponse.createByErrorMessage("运算失败");
            }
            List<String> resList = Arrays.asList(classifier.getName(),
                    regResult.getVarianceScore() + "", regResult.getAbsoluteError() + "",
                    Math.sqrt(regResult.getSquaredError()) + "", regResult.getMedianSquaredError() + "", regResult.getR2Score() + "");
            res.add(resList);
        }

        return ServerResponse.createBySuccess(res);

    }

    public Result getResult(Map<String,String> param,Integer classifierId,Classifier classifier){
        List<ClassifierParam> paramList = mClassifierParamMapper.selectByClassifierId(classifierId);
        for (ClassifierParam cp:paramList){
            if (param.containsKey(cp.getParamName())){
                cp.setDefaultValue(param.get(cp.getParamName()));
            }
        }
        classifier.setParams(paramList);
        String[] options = mLabLearnService.resolveOptions(classifier);

        File train = new File(Const.TRAIN_CLASSIFIER_FOR_WX);
        File test  = new File(Const.TEST_CLASSIFIER_FOR_WX);


        StringBuilder sb = new StringBuilder("python ");
        sb.append(classifier.getPath());
        for (int i = 0; i < options.length; i = i + 2) {
            sb.append(" ").append(options[i].trim()).append("=").append(options[i + 1].trim());
        }
        sb.append(" train=").append(train.getAbsolutePath().trim()).append(" test=").append(test.getAbsolutePath().trim());
        logger.info(sb.toString());
        String resParam = PythonUtils.execPy(sb.toString());
        Result result = mGson.fromJson(resParam, Result.class);
        return result;
    }
    public RegResult getRegResult(Map<String,String> param,Integer classifierId,Classifier classifier){

        List<ClassifierParam> paramList = mClassifierParamMapper.selectByClassifierId(classifierId);
        for (ClassifierParam cp:paramList){
            if (param.containsKey(cp.getParamName())){
                cp.setDefaultValue(param.get(cp.getParamName()));
            }
        }
        classifier.setParams(paramList);
        String[] options = mLabLearnService.resolveOptions(classifier);

        StringBuilder sb = new StringBuilder("python ");
        sb.append(classifier.getPath());
        for (int i = 0; i < options.length; i = i + 2) {
            sb.append(" ").append(options[i].trim()).append("=").append(options[i + 1].trim());
        }
        sb.append(" train=").append(Const.TRAIN_REGRESSION_FOR_WX).append(" test=").append(Const.TEST_REGRESSION_FOR_WX);
        logger.info(sb.toString());
        String resParam = PythonUtils.execPy(sb.toString());
        RegResult regResult = mGson.fromJson(resParam, RegResult.class);
        return regResult;
    }

}

