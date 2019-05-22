package com.koala.learn.service;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.ClassifierMapper;
import com.koala.learn.dao.ClassifierParamMapper;
import com.koala.learn.entity.Classifier;
import com.koala.learn.entity.ClassifierParam;
import com.koala.learn.entity.RegResult;
import com.koala.learn.entity.Result;
import com.koala.learn.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

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

    @Autowired
    JedisAdapter mJedisAdapter;

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

    public File clearIsolation(double contamination,File input) throws IOException {
        input = WekaUtils.arff2csv(input);
        File out=new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv", "") +"con_clear" +contamination+ ".csv");
        if(out.exists()){
            out=WekaUtils.csv2arff(out);
            return out;
        }
        String isoLationmdec = "python "+ Const.CLEAR_ISOLATIONFOREST_FOR_WX+ " contamination="+contamination
                +" path="+input.getAbsolutePath()+" opath="+out;
        System.out.println(isoLationmdec);
        PythonUtils.execPy(isoLationmdec);
        out=WekaUtils.csv2arff(out);
        return out;
    }


    public File handleNormalization(File input) throws IOException {
        input= WekaUtils.arff2csv(input);
        File out=new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv", "") +"Normal(0,1)" + ".csv");
        if(out.exists()){
            out=WekaUtils.csv2arff(out);
            return out;
        }
        String normaldec = "python "+ Const.NORMALIZATION_FOR_WX
                +" path="+input.getAbsolutePath()+" opath="+out;
        System.out.println(normaldec);
        PythonUtils.execPy(normaldec);
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

    public File handleWavest(File input,Integer waveLayer,Integer windowLength) throws IOException {
        input=WekaUtils.arff2csv(input);

        File out =new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv","")+
                "waveLayer_"+waveLayer + "windowLength_"+windowLength+ ".csv");
        if(out.exists()){
            out=WekaUtils.csv2arff(out);
            return out;
        }
        String waveDesc ="python " + Const.WAVE_FEATURE + " wave_layer=" + waveLayer +" len_piece="+windowLength
                + " path=" + input.getAbsolutePath() + " opath=" + out;
        System.out.println(waveDesc);
        PythonUtils.execPy(waveDesc);
        uploadXls(out);
        out=WekaUtils.csv2arff(out);
        return out;
    }

    public File handleTimeFeature(File input,Integer windowLength,
                                  Integer avg,Integer std, Integer var,
                                  Integer skew,Integer kur,Integer ptp) throws IOException {
        input = WekaUtils.arff2csv(input);
        File out = new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv", "") +
                        "windowLength_" + windowLength +"avg_" +avg + "std_" +std
                + "var_" +var+ "skew_" +skew+ "kur_" +kur+ "ptp_" + ptp+ ".csv");
        if (out.exists()) {
            out = WekaUtils.csv2arff(out);
            return out;
        }
        String timeDesc = "python " + Const.TIME_FEATURE_FOR_WX + " len_piece=" + windowLength + " avg=" +avg + " std=" +std
                + " var=" +var+ " skew=" +skew+ " kur=" +kur+ " ptp=" + ptp
                + " path=" + input.getAbsolutePath() + " opath=" + out;
        System.out.println(timeDesc);
        PythonUtils.execPy(timeDesc);
        uploadXls(out);
        out = WekaUtils.csv2arff(out);
        return out;
    }
    public String handlePCA(File input,Integer dimension) throws IOException {
        if(input.getAbsolutePath().endsWith("arff")){
            input=WekaUtils.arff2csv(input);
        }

        File out =new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv","")+
                        "dimension_"+dimension+ ".csv");

        String pcaKey = RedisKeyUtil.getPCAKey(dimension);
        if(out.exists()){
            String cacheValue = mJedisAdapter.hget(pcaKey,"explained_variance_ratio_");
            System.out.println("从缓存中获取："+cacheValue);
            return cacheValue;
        }
        String waveDesc ="python " + Const.PCA_WX + " n_components=" + dimension
                + " path=" + input.getAbsolutePath() + " opath=" + out;
        System.out.println(waveDesc);
        String res= PythonUtils.execPy(waveDesc);
        mJedisAdapter.hset(pcaKey,"explained_variance_ratio_",res);
        uploadXls(out);
        return res;
    }
    public void uploadXls(File file) throws IOException {
        if(file.getName().endsWith("csv")){
            System.out.println("将csv转为xls");
            file=FileTranslateUtil.csv2xls(file);
        }
        FTPUtil.uploadFile(Lists.newArrayList(file));
        System.out.println("上传xls完成");
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

    public String getRatio0To1(Instances instances){
        double lable0=0;
        double lable1=0;
        Attribute attributeLable =instances.attribute("lable");
        for(int i=0; i<instances.size();i++){
            Instance instance = instances.get(i);
            double lable=instance.value(attributeLable);
            if(lable==0){
                lable0++;
            }else if(lable==1){
                lable1++;
            }
        }
        double ratio=lable0/lable1;
        System.out.println(ratio);
        String ratioString=(ratio+"").substring(0,4);
        return "0与1的样本数量比为"+ratioString+":1";
    }

}

