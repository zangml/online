package com.koala.learn.service;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.dao.*;
import com.koala.learn.entity.*;
import com.koala.learn.utils.FTPUtil;
import com.koala.learn.utils.FileTranslateUtil;
import com.koala.learn.utils.PythonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ComponentApiService {



    @Autowired
    ClassifierMapper classifierMapper;

    @Autowired
    ClassifierParamMapper classifierParamMapper;

    @Autowired
    Gson gson;

    @Autowired
    APIParamMapper apiParamMapper;

    @Autowired
    APIMapper apiMapper;

    @Autowired
    UploadAlgoMapper uploadAlgoMapper;

    private static Logger logger = LoggerFactory.getLogger(ComponentApiService.class);



    /**
     * 进行预处理-异常值检测
     * @param path
     * @param opath
     * @param contamination
     * @return
     */
    public ServerResponse checkPreIso(String path, String opath, String contamination) throws IOException {

        StringBuilder sb = new StringBuilder("python ");
        sb.append(Const.FILE_ISOLATIONFOREST).append(" ");
        sb.append("contamination=").append(contamination).append(" ");
        sb.append("path=").append(path).append(" ");
        sb.append("opath=").append(opath);

        logger.info("开始执行IsolationForest预处理算法，python语句为："+sb.toString());

        PythonUtils.execPy(sb.toString());

        File opathFile=new File(opath);
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }

//        uploadCsvAndXls(opathFile);
        Map<String,Object> map=new HashMap<>();
//        map.put("csv",Const.DOWNLOAD_FILE_PREFIX+opathFile.getName());
//        map.put("xls",Const.DOWNLOAD_FILE_PREFIX+(opathFile.getName()).replace("csv","xls"));
        map.put("file_name",opathFile.getName());
        return ServerResponse.createBySuccess(map);
    }
    public void uploadCsv(File file) throws IOException {
        FTPUtil.uploadFile(Lists.newArrayList(file));
        logger.info("上传csv完成");
    }
    public void uploadXls(File file) throws IOException {
        if(file.getName().endsWith("csv")){
            logger.info("将csv转为xls");
            file= FileTranslateUtil.csv2xls(file);
        }
        FTPUtil.uploadFile(Lists.newArrayList(file));
        logger.info("上传xls完成");
    }

//    public void uploadCsvAndXls(File csvFile) throws IOException {
//        List<File> files=Lists.newArrayList(csvFile);
//        if(csvFile.getName().endsWith("csv")){
//            logger.info("将csv转为xls");
//            File xlsFile= FileTranslateUtil.csv2xls(csvFile);
//            files.add(xlsFile);
//        }
//        FTPUtil.uploadFile(files);
//    }

    public ServerResponse execSMOTE(String path, String opath, String kNeighbors, String ratio) throws Exception {
        StringBuilder sb = new StringBuilder("python ");
        sb.append(Const.FILE_SMOTE).append(" ");
        sb.append("k_neighbors=").append(kNeighbors).append(" ");
        sb.append("ratio=").append(ratio).append(" ");
        sb.append("path=").append(path).append(" ");
        sb.append("opath=").append(opath);

        logger.info("开始执行smote算法，python语句为："+sb.toString());

        PythonUtils.execPy(sb.toString());
        File opathFile=new File(opath);
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }

//        uploadCsvAndXls(opathFile);
        Map<String,Object> map=new HashMap<>();
//        map.put("csv",Const.DOWNLOAD_FILE_PREFIX+opathFile.getName());
//        map.put("xls",Const.DOWNLOAD_FILE_PREFIX+(opathFile.getName()).replace("csv","xls"));
        map.put("file_name",opathFile.getName());
        return ServerResponse.createBySuccess(map);
    }

    public ServerResponse execNormalization(String path, String opath) throws IOException {
        StringBuilder sb = new StringBuilder("python ");
        sb.append(Const.FILE_NORMALIZATION).append(" ");
        sb.append("path=").append(path).append(" ");
        sb.append("opath=").append(opath);

        logger.info("开始执行ormalization算法，python语句为："+sb.toString());

        PythonUtils.execPy(sb.toString());

        File opathFile=new File(opath);
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }

//        uploadCsvAndXls(opathFile);
        Map<String,Object> map=new HashMap<>();
//        map.put("csv",Const.DOWNLOAD_FILE_PREFIX+opathFile.getName());
//        map.put("xls",Const.DOWNLOAD_FILE_PREFIX+(opathFile.getName()).replace("csv","xls"));
        map.put("file_name",opathFile.getName());
        return ServerResponse.createBySuccess(map);
    }

    public ServerResponse execFeatureTime(String path, String opath, Integer windowLength, Integer avg, Integer std, Integer var, Integer skew, Integer kur, Integer ptp) throws IOException {

        StringBuilder sb = new StringBuilder("python ");
        sb.append(Const.FILE_TIMEFEATURE).append(" ");
        sb.append("len_piece=").append(windowLength).append(" ");
        sb.append("avg=").append(avg).append(" ");
        sb.append("std=").append(std).append(" ");
        sb.append("var=").append(var).append(" ");
        sb.append("skew=").append(skew).append(" ");
        sb.append("kur=").append(kur).append(" ");
        sb.append("ptp=").append(ptp).append(" ");
        sb.append("path=").append(path).append(" ");
        sb.append("opath=").append(opath);

        logger.info("开始执行timeFeature算法，python语句为:"+sb.toString());

        PythonUtils.execPy(sb.toString());
        File opathFile=new File(opath);
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }
//        uploadCsvAndXls(opathFile);
        Map<String,Object> map=new HashMap<>();
//        map.put("csv",Const.DOWNLOAD_FILE_PREFIX+opathFile.getName());
//        map.put("xls",Const.DOWNLOAD_FILE_PREFIX+(opathFile.getName()).replace("csv","xls"));
        map.put("file_name",opathFile.getName());
        return ServerResponse.createBySuccess(map);
    }

    public ServerResponse execFeatureFreq(String path, String opath, Integer windowLength, Integer minFre, Integer maxFre, Integer freq) throws IOException {
        StringBuilder sb = new StringBuilder("python ");
        sb.append(Const.FILE_FREQFEATURE).append(" ");
        sb.append("len_piece=").append(windowLength).append(" ");
        sb.append("min_fre=").append(minFre).append(" ");
        sb.append("max_fre=").append(maxFre).append(" ");
        sb.append("freq=").append(freq).append(" ");
        sb.append("path=").append(path).append(" ");
        sb.append("opath=").append(opath);

        logger.info("开始执行freqFeature算法，python语句为:"+sb.toString());

        PythonUtils.execPy(sb.toString());
        File opathFile=new File(opath);
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }
//        uploadCsvAndXls(opathFile);
        Map<String,Object> map=new HashMap<>();
//        map.put("csv",Const.DOWNLOAD_FILE_PREFIX+opathFile.getName());
//        map.put("xls",Const.DOWNLOAD_FILE_PREFIX+(opathFile.getName()).replace("csv","xls"));
        map.put("file_name",opathFile.getName());
        return ServerResponse.createBySuccess(map);
    }

    public ServerResponse execFeatureWav(String path, String opath, Integer windowLength, Integer waveLayer) throws IOException {
        StringBuilder sb = new StringBuilder("python ");
        sb.append(Const.FILE_WAVFEATURE).append(" ");
        sb.append("len_piece=").append(windowLength).append(" ");
        sb.append("wave_layer=").append(waveLayer).append(" ");
        sb.append("path=").append(path).append(" ");
        sb.append("opath=").append(opath);

        logger.info("开始执行waveFeature算法，python语句为:"+sb.toString());

        PythonUtils.execPy(sb.toString());
        File opathFile=new File(opath);
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }
//        uploadCsvAndXls(opathFile);
        Map<String,Object> map=new HashMap<>();
//        map.put("csv",Const.DOWNLOAD_FILE_PREFIX+opathFile.getName());
//        map.put("xls",Const.DOWNLOAD_FILE_PREFIX+(opathFile.getName()).replace("csv","xls"));
        map.put("file_name",opathFile.getName());
        return ServerResponse.createBySuccess(map);
    }

    public ServerResponse execClassify(String train, String test, Map<String, String> param,Integer classifierId) {

        Classifier classifier = classifierMapper.selectByPrimaryKey(classifierId);

        Result result =getResult(param,classifierId,classifier,train,test);
        if (result == null) {
            return ServerResponse.createByErrorMessage("运算失败");
        }
        return ServerResponse.createBySuccess(result);
    }

    public ServerResponse execRegressor(String train, String test, Map<String, String> param,Integer classifierId) {

        Classifier classifier = classifierMapper.selectByPrimaryKey(classifierId);

        RegResult regResult  =getRegResult(param,classifierId,classifier,train,test);
        if (regResult == null) {
            return ServerResponse.createByErrorMessage("运算失败");
        }
        return ServerResponse.createBySuccess(regResult);
    }
    public Result getResult(Map<String,String> param,Integer classifierId,Classifier classifier,String trainPath,String testPath){
        List<ClassifierParam> paramList = classifierParamMapper.selectByClassifierId(classifierId);
        for (ClassifierParam cp:paramList){
            if (param.containsKey(cp.getParamName())){
                cp.setDefaultValue(param.get(cp.getParamName()));
            }
        }
        classifier.setParams(paramList);
        String[] options = resolveOptions(classifier);

        File train = new File(trainPath);
        File test  = new File(testPath);

        StringBuilder sb = new StringBuilder("python ");
        sb.append(classifier.getPath());
        for (int i = 0; i < options.length; i = i + 2) {
            sb.append(" ").append(options[i].trim()).append("=").append(options[i + 1].trim());
        }
        sb.append(" train=").append(train.getAbsolutePath().trim()).append(" test=").append(test.getAbsolutePath().trim());
        logger.info(sb.toString());
        String resParam = PythonUtils.execPy(sb.toString());
        if(resParam==null || resParam.isEmpty()){
            return null;
        }
        Result result = gson.fromJson(resParam, Result.class);
        return result;
    }
    public RegResult getRegResult(Map<String,String> param, Integer classifierId, Classifier classifier,String trainPath,String testPath){

        List<ClassifierParam> paramList = classifierParamMapper.selectByClassifierId(classifierId);
        for (ClassifierParam cp:paramList){
            if (param.containsKey(cp.getParamName())){
                cp.setDefaultValue(param.get(cp.getParamName()));
            }
        }
        classifier.setParams(paramList);
        String[] options = resolveOptions(classifier);

        StringBuilder sb = new StringBuilder("python ");
        sb.append(classifier.getPath());
        for (int i = 0; i < options.length; i = i + 2) {
            sb.append(" ").append(options[i].trim()).append("=").append(options[i + 1].trim());
        }
        sb.append(" train=").append(trainPath).append(" test=").append(testPath);
        logger.info(sb.toString());
        String resParam = PythonUtils.execPy(sb.toString());
        if(resParam==null || resParam.isEmpty()){
            return null;
        }
        RegResult regResult = gson.fromJson(resParam, RegResult.class);
        regResult.setSquaredError(Math.sqrt(regResult.getSquaredError()));
        return regResult;
    }
    public String[] resolveOptions(Classifier classifier){
        List<String> optionList = new ArrayList<>();
        for (ClassifierParam cp:classifier.getParams()){
            if (StringUtils.isNotBlank(cp.getDefaultValue())){
                optionList.add(cp.getParamName());
                optionList.add(cp.getDefaultValue());
            }
        }
        String[] options = new String[optionList.size()];
        for (int i=0;i<optionList.size();i=i+2){
            options[i] =optionList.get(i);
            options[i+1] = optionList.get(i+1);
        }
        return options;
    }

    public ServerResponse execUploadPreAndFea(String path, String opath, Map<String, Object> params,Integer apiId) throws IOException {

        API api= apiMapper.selectById(apiId);
        Integer uploadAlgoId=api.getUploadAlgoId();
        UploadAlgo uploadAlgo= uploadAlgoMapper.selectById(uploadAlgoId);

        String[] options=resolveOptions(params);

        StringBuilder sb = new StringBuilder("python ");
        sb.append(uploadAlgo.getAlgoAddress());
        for (int i = 0; i < options.length; i = i + 2) {
            sb.append(" ").append(options[i].trim()).append("=").append(options[i + 1].trim());
        }
        sb.append(" path=").append(path.trim()).append(" opath=").append(opath.trim());
        logger.info(sb.toString());
        PythonUtils.execPy(sb.toString());

        File opathFile=new File(opath);
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }
//        uploadCsvAndXls(opathFile);
        Map<String,Object> map=new HashMap<>();
//        map.put("csv",Const.DOWNLOAD_FILE_PREFIX+opathFile.getName());
//        map.put("xls",Const.DOWNLOAD_FILE_PREFIX+(opathFile.getName()).replace("csv","xls"));
        map.put("file_name",opathFile.getName());
        return ServerResponse.createBySuccess(map);

    }

    public String[] resolveOptions(Map<String,Object> param){
        List<String> paramList = new ArrayList<>();
        for (String key:param.keySet()){
            if (StringUtils.isNotBlank(param.get(key).toString())){
                paramList.add(key);
                paramList.add(param.get(key).toString());
            }
        }
        String[] options;
        if (paramList.size()>0){
            options = new String[paramList.size()];
            for (int i=0;i<options.length;i++){
                options[i] = paramList.get(i);
            }
            return options;
        }
        return null;
    }

    public ServerResponse execUploadML(String train, String test, Map<String, Object> params, Integer apiId,Integer apiType) {
        API api= apiMapper.selectById(apiId);
        Integer uploadAlgoId=api.getUploadAlgoId();
        UploadAlgo uploadAlgo= uploadAlgoMapper.selectById(uploadAlgoId);

        String[] options=resolveOptions(params);

        StringBuilder sb = new StringBuilder("python ");
        sb.append(uploadAlgo.getAlgoAddress());
        for (int i = 0; i < options.length; i = i + 2) {
            sb.append(" ").append(options[i].trim()).append("=").append(options[i + 1].trim());
        }
        sb.append(" train=").append(train.trim()).append(" test=").append(test.trim());
        logger.info(sb.toString());
        String strResult = PythonUtils.execPy(sb.toString());
        if(apiType.equals(3)){
            Result result = gson.fromJson(strResult, Result.class);
            return ServerResponse.createBySuccess(result);
        }else {
            RegResult regResult=gson.fromJson(strResult,RegResult.class);
            return ServerResponse.createBySuccess(regResult);
        }
    }

    public API getAPIById(Integer id){
        return apiMapper.selectById(id);
    }



}
