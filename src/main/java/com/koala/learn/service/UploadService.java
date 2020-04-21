package com.koala.learn.service;

import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.dao.*;
import com.koala.learn.entity.*;
import com.koala.learn.utils.PythonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@Service
public class UploadService {

    @Autowired
    LabDesignBGService labDesignBGService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ClassifierMapper mClassifierMapper;

    @Autowired
    HostHolder holder;

    @Autowired
    Gson gson;

    @Autowired
    UploadAlgoMapper uploadAlgoMapper;

    @Autowired
    APIMapper apiMapper;

    @Autowired
    FeatureMapper featureMapper;

    @Autowired
    APIParamMapper apiParamMapper;

    @Autowired
    FeatureParamMapper featureParamMapper;

    private static final Logger logger= LoggerFactory.getLogger(UploadService.class);

    public ServerResponse uploadClassifierModel(MultipartFile uploadModel, MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params, Integer resultType) throws IOException {

        //保存模型文件
        String uploadModelName=uploadModel.getOriginalFilename();
        String newModelName=labDesignBGService.getFileName("model",uploadModelName);
        File modelFile = new File(Const.UPDATE_CLASS_ROOT_MODEL,newModelName);
        uploadModel.transferTo(modelFile);

        //保存算法文件
        String uploadFileName=uploadFile.getOriginalFilename();
        String newFileName=labDesignBGService.getFileName("model_cla",uploadFileName);
        File claFile = new File(Const.UPDATE_CLASS_ROOT_MODEL,newFileName);
        uploadFile.transferTo(claFile);


        //保存测试数据
        String testFileName=testFile.getOriginalFilename();
        String newTestName=labDesignBGService.getFileName("model_cla",testFileName);
        File test = new File(Const.UPLOAD_DATASET,newTestName);
        testFile.transferTo(test);

        String opath=Const.UPLOAD_DATASET+"out_"+newTestName;
        StringBuilder sb = new StringBuilder("python ");
        sb.append(claFile.getAbsolutePath()).append(" ");
        sb.append("model=").append(modelFile.getAbsolutePath());
        sb.append(" test=").append(test.getAbsolutePath());
        if(resultType.equals(2) || resultType.equals(3)){
            sb.append(" opath=").append(opath);
        }

        Model model=new Model();
        API api=new API();
        UploadAlgo uploadAlgo=new UploadAlgo();

        logger.info("开始测试上传的分类模型，python语句为："+sb.toString());

        if(resultType.equals(1)){
            String res = PythonUtils.execPy(sb.toString());
            logger.info("得到结果："+res);
            Result result;
            try{
                result=gson.fromJson(res,Result.class);
            }catch (Exception e){
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("算法验证失败");
            }
            if(result==null){
                return ServerResponse.createByErrorMessage("算法验证失败");
            }
            model.setModelType(Const.CLASSIFIER);
            api.setApiType(Const.UPLOAD_ALGO_TYPE_MODEL_CLA);
            uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_MODEL_CLA);
        }else if(resultType.equals(2)){

            PythonUtils.execPy(sb.toString());
            File opathFile=new File(opath);
            if(!opathFile.exists()){
                return ServerResponse.createByErrorMessage("失败");
            }
            model.setModelType(Const.CLASSIFIER_PREDICT);
            api.setApiType(Const.UPLOAD_ALGO_TYPE_MODEL_PREDICT_CLA);
            uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_MODEL_PREDICT_CLA);

        }else if(resultType.equals(3)){
            String res = PythonUtils.execPy(sb.toString());
            logger.info("得到结果："+res);
            Result result;
            try{
                result=gson.fromJson(res,Result.class);
            }catch (Exception e){
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("算法验证失败");
            }
            if(result==null){
                return ServerResponse.createByErrorMessage("算法验证失败");
            }
            File opathFile=new File(opath);
            if(!opathFile.exists()){
                return ServerResponse.createByErrorMessage("获取预测结果失败");
            }
            model.setModelType(Const.CLASSIFIER_PREDICT_RESULT);
            api.setApiType(Const.UPLOAD_ALGO_TYPE_MODEL_PREDICT_RES_CLA);
            uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_MODEL_PREDICT_RES_CLA);
        }

        model.setModelName(params.get("name").toString());
        model.setModelDesc(params.get("des").toString());

        model.setCreatTime(new Date());
        model.setUpdateTime(new Date());
        model.setFileAddress(modelFile.getAbsolutePath());
        model.setStatus(1);

        int countModel = modelMapper.insert(model);

        if(countModel<=0){
            return ServerResponse.createByErrorMessage("分类算法模型保存错误!");
        }

        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());

//        uploadAlgo.setAlgoDependence(params.get("dependence").toString());


        Classifier classifier = new Classifier();
        classifier.setDes(params.get("des").toString());
        classifier.setName(params.get("name").toString());
        classifier.setLabId(-1);
        classifier.setPath(claFile.getAbsolutePath());
        int count = mClassifierMapper.insert(classifier);

        if(count<=0){
            return ServerResponse.createByErrorMessage("分类算法保存错误!");
        }


        uploadAlgo.setAlgoId(classifier.getId());
        uploadAlgo.setUserId(holder.getUser().getId());
        uploadAlgo.setTestFile(test.getAbsolutePath());
        uploadAlgo.setAlgoAddress(claFile.getAbsolutePath());
        int insert = uploadAlgoMapper.insert(uploadAlgo);
        if(insert<=0){
            return ServerResponse.createByErrorMessage("分类算法保存上传信息错误!");
        }

        api.setUploadAlgoId(uploadAlgo.getId());
        api.setContentType("application/x-www-form-urlencoded");
        api.setCreatTime(new Date());
        api.setDesc(uploadAlgo.getAlgoDes());
        api.setName(uploadAlgo.getAlgoName());
        api.setRequestMethod("POST");
        api.setStatus(0);
        api.setUserId(uploadAlgo.getUserId());
        api.setUpdateTime(new Date());
        api.setPub(1);

        api.setUrl("https://api.phmlearn.com/component/upload/ML/model/"+model.getId()+"/"+uploadAlgo.getId());

        int apiInsetCount = apiMapper.insert(api);
        if(apiInsetCount<=0){
            return ServerResponse.createByErrorMessage("保存API信息错误");
        }

        return ServerResponse.createBySuccess();
    }

    public ServerResponse uploadRegModel(MultipartFile uploadModel, MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params, Integer resultType) throws IOException {

        //保存模型文件
        String uploadModelName=uploadModel.getOriginalFilename();
        String newModelName=labDesignBGService.getFileName("model",uploadModelName);
        File modelFile = new File(Const.UPDATE_CLASS_ROOT_MODEL,newModelName);
        uploadModel.transferTo(modelFile);

        //保存算法文件
        String uploadFileName=uploadFile.getOriginalFilename();
        String newFileName=labDesignBGService.getFileName("model_reg",uploadFileName);
        File claFile = new File(Const.UPDATE_CLASS_ROOT_MODEL,newFileName);
        uploadFile.transferTo(claFile);


        //保存测试数据
        String testFileName=testFile.getOriginalFilename();
        String newTestName=labDesignBGService.getFileName("model_reg",testFileName);
        File test = new File(Const.UPLOAD_DATASET,newTestName);
        testFile.transferTo(test);

        String opath=Const.UPLOAD_DATASET+"out_"+newTestName;
        StringBuilder sb = new StringBuilder("python ");
        sb.append(claFile.getAbsolutePath()).append(" ");
        sb.append("model=").append(modelFile.getAbsolutePath());
        sb.append(" test=").append(test.getAbsolutePath());
        if(resultType.equals(2) || resultType.equals(3)){
            sb.append(" opath=").append(opath);
        }

        Model model=new Model();
        UploadAlgo uploadAlgo=new UploadAlgo();
        API api=new API();

        logger.info("开始测试上传的回归模型，python语句为："+sb.toString());

        if(resultType.equals(1)){
            String res = PythonUtils.execPy(sb.toString());
            logger.info("得到结果："+res);
            RegResult regResult;
            try{
                regResult=gson.fromJson(res,RegResult.class);
            }catch (Exception e){
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("算法验证失败");
            }
            if(regResult==null){
                return ServerResponse.createByErrorMessage("算法验证失败");
            }
            model.setModelType(Const.REGRESSION);
            uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_MODEL_REG);
            api.setApiType(Const.UPLOAD_ALGO_TYPE_MODEL_REG);
        }else if(resultType.equals(2)){

            PythonUtils.execPy(sb.toString());
            File opathFile=new File(opath);
            if(!opathFile.exists()){
                return ServerResponse.createByErrorMessage("失败");
            }
            model.setModelType(Const.REGRESSION_PREDICT);
            uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_MODEL_PREDICT_REG);
            api.setApiType(Const.UPLOAD_ALGO_TYPE_MODEL_PREDICT_REG);

        }else if(resultType.equals(3)){
            String res = PythonUtils.execPy(sb.toString());
            logger.info("得到结果："+res);
            RegResult regResult;
            try{
                regResult=gson.fromJson(res,RegResult.class);
            }catch (Exception e){
                e.printStackTrace();
                return ServerResponse.createByErrorMessage("算法验证失败");
            }
            if(regResult==null){
                return ServerResponse.createByErrorMessage("算法验证失败");
            }
            File opathFile=new File(opath);
            if(!opathFile.exists()){
                return ServerResponse.createByErrorMessage("获取预测结果失败");
            }
            model.setModelType(Const.REGRESSION_PREDICT_RESULT);
            uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_MODEL_PREDICT_RES_REG);
            api.setApiType(Const.UPLOAD_ALGO_TYPE_MODEL_PREDICT_RES_REG);
        }
        model.setModelName(params.get("name").toString());
        model.setModelDesc(params.get("des").toString());
        model.setCreatTime(new Date());
        model.setUpdateTime(new Date());
        model.setFileAddress(modelFile.getAbsolutePath());
        model.setStatus(1);

        int countModel = modelMapper.insert(model);

        if(countModel<=0){
            return ServerResponse.createByErrorMessage("回归算法模型保存错误!");
        }


        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());

//        uploadAlgo.setAlgoDependence(params.get("dependence").toString());


        Classifier classifier = new Classifier();
        classifier.setDes(params.get("des").toString());
        classifier.setName(params.get("name").toString());
        classifier.setLabId(-1);
        classifier.setPath(claFile.getAbsolutePath());
        int count = mClassifierMapper.insert(classifier);

        if(count<=0){
            return ServerResponse.createByErrorMessage("回归算法保存错误!");
        }


        uploadAlgo.setAlgoId(classifier.getId());
        uploadAlgo.setUserId(holder.getUser().getId());
        uploadAlgo.setTestFile(test.getAbsolutePath());
        uploadAlgo.setAlgoAddress(claFile.getAbsolutePath());
        int insert = uploadAlgoMapper.insert(uploadAlgo);
        if(insert<=0){
            return ServerResponse.createByErrorMessage("回归算法保存上传信息错误!");
        }

        api.setUploadAlgoId(uploadAlgo.getId());
        api.setContentType("application/x-www-form-urlencoded");
        api.setCreatTime(new Date());
        api.setDesc(uploadAlgo.getAlgoDes());
        api.setName(uploadAlgo.getAlgoName());
        api.setRequestMethod("POST");
        api.setStatus(0);
        api.setUserId(uploadAlgo.getUserId());
        api.setUpdateTime(new Date());
        api.setPub(1);

        api.setUrl("https://api.phmlearn.com/component/upload/ML/model/"+model.getId()+"/"+uploadAlgo.getId());

        int apiInsetCount = apiMapper.insert(api);
        if(apiInsetCount<=0){
            return ServerResponse.createByErrorMessage("保存API信息错误");
        }

        return ServerResponse.createBySuccess();
    }


    public ServerResponse uploadPre(MultipartFile uploadFile, Map<String, Object> params) throws IOException {

        //保存算法文件
        String uploadFileName=uploadFile.getOriginalFilename();
        String newFileName=labDesignBGService.getFileName("pre",uploadFileName);
        File preFile = new File(Const.UPDATE_CLASS_ROOT_PRE,newFileName);
        uploadFile.transferTo(preFile);


        String opath=Const.FILE_OPATH_PRE_ZHOUCHENG+"out_"+newFileName+".csv";

        File opathFile=new File(opath);

        StringBuilder sb = new StringBuilder("python ");
        sb.append(preFile).append(" ");
        sb.append("path=").append(Const.DATA_ZHOUCHENG).append(" ");
        sb.append("opath=").append(opath);

        logger.info(sb.toString());
        PythonUtils.execPy(sb.toString());
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }

        UploadAlgo uploadAlgo=new UploadAlgo();
        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());
        uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_PRE);

        Feature feature=new Feature();
        feature.setName(params.get("name").toString());
        feature.setDes(params.get("des").toString());
        feature.setLabel(preFile.getName());
        feature.setFeatureType(-1); //待审核的特征提取算法

        int count = featureMapper.insert(feature);
        if(count<=0){
            return ServerResponse.createByErrorMessage("算法保存错误!");
        }

        uploadAlgo.setAlgoId(feature.getId());
        uploadAlgo.setUserId(holder.getUser().getId());
//            uploadAlgo.setTestFile(test.getAbsolutePath());
        uploadAlgo.setAlgoAddress(preFile.getAbsolutePath());
        int insert = uploadAlgoMapper.insert(uploadAlgo);
        if(insert<=0){
            return ServerResponse.createByErrorMessage("上传保存错误!");
        }
        API api=new API();

        api.setApiType(Const.UPLOAD_ALGO_TYPE_PRE);
        api.setUploadAlgoId(uploadAlgo.getId());
        api.setContentType("application/x-www-form-urlencoded");
        api.setCreatTime(new Date());
        api.setDesc(uploadAlgo.getAlgoDes());
        api.setName(uploadAlgo.getAlgoName());
        api.setRequestMethod("POST");
        api.setStatus(0);
        api.setUserId(uploadAlgo.getUserId());
        api.setUpdateTime(new Date());

        api.setPub(Integer.parseInt(params.get("pub").toString()));
        api.setUrl("https://api.phmlearn.com/component/upload/zhoucheng/"+Const.UPLOAD_ALGO_TYPE_PRE+"/"+uploadAlgo.getId());

        int apiInsertCount = apiMapper.insert(api);
        if(apiInsertCount<=0){
            return ServerResponse.createByErrorMessage("保存API信息错误");
        }

//
        //保存参数信息
        for (String key:params.keySet()) {
            if (key.startsWith("paramName")) {
                if (StringUtils.isNotBlank(params.get(key).toString())) {

                    APIParam apiParam = new APIParam();
                    apiParam.setName(params.get(key).toString());
                    apiParam.setCreatTime(new Date());
                    apiParam.setDefaultValue(params.get(key.replace("Name", "Value")).toString());
                    apiParam.setParamDesc(params.get(key.replace("Name", "Des")).toString());
                    apiParam.setIsNecessary(params.get(key.replace("Name", "Necessary")).toString());
                    apiParam.setParamType(params.get(key.replace("Name", "Type")).toString());
                    apiParam.setStatus(1);
                    apiParam.setUpdateTime(new Date());
                    apiParam.setAPIId(api.getId());
                    apiParamMapper.insert(apiParam);

                    FeatureParam featureParam = new FeatureParam();
                    featureParam.setFeatureId(feature.getId());
                    featureParam.setShell(params.get(key).toString());
                    featureParam.setName(params.get(key).toString());
                    featureParam.setDes(params.get(key.replace("Name", "Des")).toString());
                    featureParam.setDefaultValue(params.get(key.replace("Name", "Value")).toString());
                    featureParamMapper.insert(featureParam);

                }
            }
        }
        return ServerResponse.createBySuccess();
    }

    public ServerResponse uploadFea(MultipartFile uploadFile, Map<String, Object> params) throws IOException {

        //保存算法文件
        String uploadFileName=uploadFile.getOriginalFilename();
        String newFileName=labDesignBGService.getFileName("fea",uploadFileName);
        File preFile = new File(Const.UPDATE_CLASS_ROOT_FEA,newFileName);
        uploadFile.transferTo(preFile);


        String opath=Const.FILE_OPATH_PRE_ZHOUCHENG+"out_"+newFileName+".csv";

        File opathFile=new File(opath);

        StringBuilder sb = new StringBuilder("python ");
        sb.append(preFile).append(" ");
        sb.append("path=").append(Const.DATA_ZHOUCHENG).append(" ");
        sb.append("opath=").append(opath);

        logger.info(sb.toString());
        PythonUtils.execPy(sb.toString());
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }

        UploadAlgo uploadAlgo=new UploadAlgo();
        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());
        uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_FEA);


        Feature feature=new Feature();
        feature.setName(params.get("name").toString());
        feature.setDes(params.get("des").toString());
        feature.setLabel(preFile.getName());
        feature.setFeatureType(-2); //待审核的预处理算法

        int count = featureMapper.insert(feature);
        if(count<=0){
            return ServerResponse.createByErrorMessage("算法保存错误!");
        }

        uploadAlgo.setAlgoId(feature.getId());
        uploadAlgo.setUserId(holder.getUser().getId());
//            uploadAlgo.setTestFile(test.getAbsolutePath());
        uploadAlgo.setAlgoAddress(preFile.getAbsolutePath());
        int insert = uploadAlgoMapper.insert(uploadAlgo);
        if(insert<=0){
            return ServerResponse.createByErrorMessage("上传保存错误!");
        }
        API api=new API();

        api.setApiType(Const.UPLOAD_ALGO_TYPE_FEA);
        api.setUploadAlgoId(uploadAlgo.getId());
        api.setContentType("application/x-www-form-urlencoded");
        api.setCreatTime(new Date());
        api.setDesc(uploadAlgo.getAlgoDes());
        api.setName(uploadAlgo.getAlgoName());
        api.setRequestMethod("POST");
        api.setStatus(0);
        api.setUserId(uploadAlgo.getUserId());
        api.setUpdateTime(new Date());

        api.setPub(Integer.parseInt(params.get("pub").toString()));
        api.setUrl("https://api.phmlearn.com/component/upload/zhoucheng/"+Const.UPLOAD_ALGO_TYPE_FEA+"/"+uploadAlgo.getId());

        int apiInsertCount = apiMapper.insert(api);
        if(apiInsertCount<=0){
            return ServerResponse.createByErrorMessage("保存API信息错误");
        }

//
        //保存参数信息
        for (String key:params.keySet()) {
            if (key.startsWith("paramName")) {
                if (StringUtils.isNotBlank(params.get(key).toString())) {

                    APIParam apiParam = new APIParam();
                    apiParam.setName(params.get(key).toString());
                    apiParam.setCreatTime(new Date());
                    apiParam.setDefaultValue(params.get(key.replace("Name", "Value")).toString());
                    apiParam.setParamDesc(params.get(key.replace("Name", "Des")).toString());
                    apiParam.setIsNecessary(params.get(key.replace("Name", "Necessary")).toString());
                    apiParam.setParamType(params.get(key.replace("Name", "Type")).toString());
                    apiParam.setStatus(1);
                    apiParam.setUpdateTime(new Date());
                    apiParam.setAPIId(api.getId());
                    apiParamMapper.insert(apiParam);

                    FeatureParam featureParam = new FeatureParam();
                    featureParam.setFeatureId(feature.getId());
                    featureParam.setShell(params.get(key).toString());
                    featureParam.setName(params.get(key).toString());
                    featureParam.setDes(params.get(key.replace("Name", "Des")).toString());
                    featureParam.setDefaultValue(params.get(key.replace("Name", "Value")).toString());
                    featureParamMapper.insert(featureParam);

                }
            }
        }
        return ServerResponse.createBySuccess();
    }
}
