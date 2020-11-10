package com.koala.learn.service;

import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.component.HostHolder;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.*;
import com.koala.learn.entity.*;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.treat.WxViewUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AlgorithmService {

    @Autowired
    AlgorithmMapper algorithmMapper;

    @Autowired
    JedisAdapter mJedisAdapter;

    @Autowired
    WxComponentService wxComponentService;

    @Autowired
    LabDesignBGService labDesignBGService;

    @Autowired
    FeatureMapper featureMapper;

    @Autowired
    FeatureParamMapper featureParamMapper;

    @Autowired
    HostHolder mHolder;

    @Autowired
    UploadAlgoMapper uploadAlgoMapper;

    @Autowired
    APIMapper apiMapper;

    @Autowired
    APIParamMapper apiParamMapper;

    public Algorithm getAlgoById(Integer id){
        return algorithmMapper.getById(id);
    }

    public int updateByPrimaryKeySelective(Algorithm algorithm){
        return algorithmMapper.updateByPrimaryKeySelective(algorithm);
    }

    public List<Algorithm> getAllAlgos(){
        return  algorithmMapper.selectAllAlgorithm();
    }
    public String initSmoteEchart(){
        String key = RedisKeyUtil.getInitSmote();
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        try {
            Instances instances = new Instances(new FileReader(Const.DATA_FOR_SMOTE));
            EchatsOptions options = WxViewUtils.reslovePCA(instances, 12);
            Gson gson = new Gson();
            String optionsCache = gson.toJson(options);
            mJedisAdapter.set(key, optionsCache);
            return optionsCache;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String initIsolationForestEchart() throws Exception {
        String key=RedisKeyUtil.getIsolationKey();
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        File input =new File(Const.DATA_FOR_INIT_ISOLATIONFOREST);
        Instances instances=new Instances(new FileReader(input));
        EchatsOptions options =WxViewUtils.reslovePCA(instances,1);
        Gson gson=new Gson();
        String cacheOptions=gson.toJson(options);
        mJedisAdapter.set(key,cacheOptions);

        return cacheOptions;
    }

    public String getSmoteEchart(Map<String,Object> param) throws Exception {
        Integer ratio= Integer.parseInt((String) param.get("-P"));
        Integer kNeighbors=5;
        if(ratio<=0){
            return initSmoteEchart();
        }
        String key=RedisKeyUtil.getSmoteKey(kNeighbors,ratio);
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        Instances instances = new Instances(new FileReader(Const.DATA_FOR_SMOTE));
        instances.setClassIndex(instances.numAttributes() - 1);
        SMOTE smote =new SMOTE();
        String[] options = {"-S", String.valueOf(1), "-P", String.valueOf(ratio), "-K", String.valueOf(kNeighbors)};
        smote.setInputFormat(instances);
        smote.setOptions(options);
        Instances instances1 = Filter.useFilter(instances, smote);
        EchatsOptions echatsOptions = WxViewUtils.reslovePCA(instances1,12);
        Gson gson = new Gson();
        String cacheOptions=gson.toJson(echatsOptions);
        mJedisAdapter.set(key,cacheOptions);
        return cacheOptions;
    }

    public String getIsoForestEchart(Map<String, Object> param) throws Exception {
        Double con=Double.parseDouble((String) param.get("-i"));
        if(con<0 || con>1){
            return null;
        }
        String key=RedisKeyUtil.getIsolationKey(con);

        String cache=mJedisAdapter.get(key);
        Gson gson=new Gson();

        if(cache!=null){
            return cache;
        }
        File input =new File(Const.DATA_FOR_ISOLATIONFOREST);

        File out=wxComponentService.handleIsolation(con,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options =WxViewUtils.reslovePCA(instances,1);

        String cacheOptions=gson.toJson(options);

        mJedisAdapter.set(key,cacheOptions);

        return cacheOptions;
    }

    public String initNormalizationEchart() throws Exception {
        String key=RedisKeyUtil.getNormalizationKey();
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        EchatsOptions options= WxViewUtils.resloveNormalization(new Instances(new FileReader(Const.DATA_FOR_NORMALIZATION)));
        options.setTitle(new EchatsOptions.TitleBean("归一化前数据分布","以每个特征的样本均值表示"));
        Gson gson=new Gson();
        String cacheOptions=gson.toJson(options);
        mJedisAdapter.set(key,cacheOptions);
        return cacheOptions;
    }

    public String getNormalizationEchart() throws Exception {
        String key=RedisKeyUtil.getNormalizationKey("(0,1)");
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        File input =new File(Const.DATA_FOR_NORMALIZATION);
        File out=wxComponentService.handleNormalization(input);
        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
        EchatsOptions options =WxViewUtils.resloveNormalization(instances);
        options.setTitle(new EchatsOptions.TitleBean("归一化后数据分布","以每个特征的样本均值表示"));
        Gson gson=new Gson();
        String cacheOptions=gson.toJson(options);
        mJedisAdapter.set(key,cacheOptions);
        return cacheOptions;
    }

    public String getFFTEchart(Map<String, Object> param) throws IOException {
        String attribute= (String) param.get("-a");
        String key=RedisKeyUtil.getfftKey(attribute);
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        File input =new File(Const.DATA_FOR_FFT_WX);
        File out=wxComponentService.handleFFT(attribute,input);
        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
        EchatsOptions options=WxViewUtils.resloveFFT(instances,1);
        Gson gson=new Gson();
        String cacheOptions=gson.toJson(options);
        mJedisAdapter.set(key,cacheOptions);
        return cacheOptions;
    }




    public ServerResponse uploadFea(MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params) throws IOException {
        //保存算法文件
        String uploadFileName=uploadFile.getOriginalFilename();
        String newFileName=labDesignBGService.getFileName("fea",uploadFileName);
        File feaFile = new File(Const.UPDATE_CLASS_ROOT_FEA,newFileName);
        uploadFile.transferTo(feaFile);


        String opath=Const.FILE_OPATH_FEA_ZHOUCHENG+"out_"+newFileName+".csv";

        File opathFile=new File(opath);

        StringBuilder sb = new StringBuilder("python ");
        sb.append(feaFile).append(" ");
        sb.append("path=").append(Const.DATA_ZHOUCHENG).append(" ");
        sb.append("opath=").append(opath);

        System.out.println(sb.toString());
        PythonUtils.execPy(sb.toString());
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }

        UploadAlgo uploadAlgo=new UploadAlgo();
        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());
        uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_FEA);

        uploadAlgo.setAlgoDependence(params.get("dependence").toString());

        Feature feature=new Feature();
        feature.setName(params.get("name").toString());
        feature.setDes(params.get("des").toString());
        feature.setLabel(feaFile.getName());
        feature.setFeatureType(-2); //待审核的特征提取算法

        int count = featureMapper.insert(feature);
        if(count<=0){
            return ServerResponse.createByErrorMessage("算法保存错误!");
        }

        uploadAlgo.setAlgoId(feature.getId());
        uploadAlgo.setUserId(mHolder.getUser().getId());
//            uploadAlgo.setTestFile(test.getAbsolutePath());
        uploadAlgo.setAlgoAddress(feaFile.getAbsolutePath());
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
        api.setUrl("https://api.phmlearn.com/component/upload/"+Const.UPLOAD_ALGO_TYPE_FEA+"/"+uploadAlgo.getId());

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

    public ServerResponse uploadClassifier(MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params) {
        return ServerResponse.createByErrorMessage("上传失败");
    }

    public ServerResponse uploadRegressor(MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params) {
        return ServerResponse.createByErrorMessage("上传失败");
    }

    public ServerResponse uploadPre(MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params) throws IOException {
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

        System.out.println(sb.toString());
        PythonUtils.execPy(sb.toString());
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }

        UploadAlgo uploadAlgo=new UploadAlgo();
        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());
        uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_PRE);

        uploadAlgo.setAlgoDependence(params.get("dependence").toString());

        Feature feature=new Feature();
        feature.setName(params.get("name").toString());
        feature.setDes(params.get("des").toString());
        feature.setLabel(preFile.getName());
        feature.setFeatureType(-2); //待审核的特征提取算法

        int count = featureMapper.insert(feature);
        if(count<=0){
            return ServerResponse.createByErrorMessage("算法保存错误!");
        }

        uploadAlgo.setAlgoId(feature.getId());
        uploadAlgo.setUserId(mHolder.getUser().getId());
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
        api.setUrl("https://api.phmlearn.com/component/upload/"+Const.UPLOAD_ALGO_TYPE_PRE+"/"+uploadAlgo.getId());

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

    public List<Algorithm> getAlgoByTypeId(Integer typeId) {
        return algorithmMapper.getAlgosByTypeId(typeId);
    }
}
