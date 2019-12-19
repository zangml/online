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
import com.koala.learn.utils.WekaUtils;
import com.koala.learn.utils.divider.IDivider;
import com.koala.learn.utils.feature.IFeature;
import com.koala.learn.vo.FeatureVo;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.CSVSaver;
import weka.core.pmml.jaxbbindings.Regression;

/**
 * Created by koala on 2018/1/12.
 */
@Service
public class LabDesignBGService {

    private static Logger logger = LoggerFactory.getLogger(LabDesignBGService.class);
    @Autowired
    JedisAdapter mJedisAdapter;
    @Autowired
    ClassifierMapper mClassifierMapper;

    @Autowired
    FeatureMapper featureMapper;

    @Autowired
    FeatureParamMapper mFeatureParamMapper;

    @Autowired
    ClassifierParamMapper mClassifierParamMapper;

    @Autowired
    DividerMapper mDividerMapper;

    @Autowired
    LabGroupMapper groupMapper;

    @Autowired
    HostHolder mHolder;

    @Autowired
    BuildinFileMapper fileMapper;

    @Autowired
    UploadAlgoMapper uploadAlgoMapper;
    @Autowired
    Gson mGson;

    public BuildinFile getFileById(int id){
        return fileMapper.selectByPrimaryKey(id);
    }

    public void saveFeature(Lab lab, Feature feature, Map<String,String> map){

        String featureKey = RedisKeyUtil.getFeatureKey(lab.getId());
        List<FeatureParam> paramList = mFeatureParamMapper.selectAllByFeatureId(feature.getId());

        FeatureVo vo = new FeatureVo();
        vo.setFeature(feature);
        for (FeatureParam param:paramList){
            if (map.get(param.getShell()) != null){
                param.setDefaultValue(map.get(param.getShell()));
            }
        }
        vo.setParamList(paramList);
        Gson gson = new Gson();
        String featureInstanceKey = RedisKeyUtil.getFeatureInstanceKey(lab.getId(),-1);
        mJedisAdapter.lpush(featureKey,gson.toJson(vo));
        mJedisAdapter.lpush(featureInstanceKey,gson.toJson(vo));
    }
    public void savePre(Lab lab, Feature feature, Map<String,String> map){

        String preKey = RedisKeyUtil.getPreKey(lab.getId());
        List<FeatureParam> paramList = mFeatureParamMapper.selectAllByFeatureId(feature.getId());

        FeatureVo vo = new FeatureVo();
        vo.setFeature(feature);
        for (FeatureParam param:paramList){
            if (map.get(param.getShell()) != null){
                param.setDefaultValue(map.get(param.getShell()));
            }
        }
        vo.setParamList(paramList);
        Gson gson = new Gson();
        String featureInstanceKey = RedisKeyUtil.getFeatureInstanceKey(lab.getId(),-1);
        mJedisAdapter.lpush(featureInstanceKey,gson.toJson(vo));
        mJedisAdapter.lpush(preKey,gson.toJson(vo));
    }
    public File addPre(HttpSession session, Feature feature, Map<String,String> param, Lab lab)  {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        IFeature filter = (IFeature) ac.getBean(feature.getLabel());
        List<String> paramList = new ArrayList<>();
        for (String key:param.keySet()){
            if (StringUtils.isNotBlank(param.get(key))){
                paramList.add(key);
                paramList.add(param.get(key));
            }
        }
        String[] options;
        if (paramList.size()>0){
            options = new String[paramList.size()];
            for (int i=0;i<options.length;i++){
                options[i] = paramList.get(i);
            }
            filter.setOptions(options);
        }

        File input = new File(lab.getFile().replace("csv","arff"));
        System.out.println("数据预处理：input为原始文件");

        ArffLoader loader = new ArffLoader();
        try {
            loader.setSource(input);
            Instances instances = loader.getDataSet();
            instances.setClassIndex(instances.numAttributes()-1);
            System.out.println("feature:"+feature.getName());
            File out = new File(input.getParent(), input.getName().replace(".arff", "") + "_afterPre" + ".csv");
            filter.filter(instances, input, out);
            out= WekaUtils.csv2arff(out);
            return out;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }



    }

    public File addFeature(HttpSession session, Feature feature, Map<String,String> param, Lab lab)  {
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        IFeature filter = (IFeature) ac.getBean(feature.getLabel());
        List<String> paramList = new ArrayList<>();
        for (String key:param.keySet()){
            if (StringUtils.isNotBlank(param.get(key))){
                paramList.add(key);
                paramList.add(param.get(key));
            }
        }
        String[] options;
        StringBuilder name = new StringBuilder(feature.getLabel());
        if (paramList.size()>0){
            options = new String[paramList.size()];
            for (int i=0;i<options.length;i++){
                options[i] = paramList.get(i);
                name.append(paramList.get(i));
            }
            filter.setOptions(options);
        }

        String filePreKey = RedisKeyUtil.getFilePreKey(lab.getId());
        File input = null;
        if (mJedisAdapter.llen(filePreKey) >0){
            System.out.println("特征提取："+feature.getName());
            input = new File(mJedisAdapter.lrange(filePreKey,0,1).get(0));
            System.out.println("input:"+input.getName());
        }else {
            input = new File(lab.getFile().replace("csv","arff"));
        }

        ArffLoader loader = new ArffLoader();
        try {
            System.out.println(input.getAbsolutePath());
            loader.setSource(input);
            Instances instances = loader.getDataSet();
            instances.setClassIndex(instances.numAttributes()-1);
            if(feature.getId()>=5) {
                System.out.println("feature.getId()>=5");
                File out = new File(input.getParent(), input.getName().replace(".arff", "") + name + ".csv");
                filter.filter(instances, input, out);
                out= WekaUtils.csv2arff(out);
                return out;
            }else{
                File out = new File(input.getParent(), input.getName().replace(".arff", "") + name + ".arff");
                filter.filter(instances, input, out);
                return out;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }

    }

    public void saveClassifier(Map<String,String> param,Integer labId){
        Classifier classifier = mClassifierMapper.selectByLabel(param.get("classifier"));
        List<ClassifierParam> paramList = mClassifierParamMapper.selectByClassifierId(classifier.getId());
        for (ClassifierParam cp:paramList){
            if (param.containsKey(cp.getParamName())){
                cp.setDefaultValue(param.get(cp.getParamName()));
            }
        }
        classifier.setParams(paramList);
        String classifierKey = RedisKeyUtil.getClassifierKey(labId);
        Gson gson = new Gson();
        mJedisAdapter.lpush(classifierKey,gson.toJson(classifier));
    }

    public void saveDivider(Map<String,String> param,Integer labId,HttpSession session){
        String key = RedisKeyUtil.getDividerKey(labId);
        String id = param.get("dividerId");
        Divider d = mDividerMapper.selectByPrimaryKey(Integer.valueOf(id));
        d.setRadio(param.get("radio"));
        Gson gson = new Gson();
        mJedisAdapter.set(key,gson.toJson(d));
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        IDivider divider = (IDivider) ac.getBean(param.get("type"));

        String fileKey = RedisKeyUtil.getFileKey(labId);
        long length = mJedisAdapter.llen(fileKey);
        param.put("labId",labId+"");
        if (length>0){
            String path = mJedisAdapter.lrange(fileKey,0,1).get(0);
            File input = new File(path);
            divider.divide(input,param);
        }
    }

    public ServerResponse finish(Integer groupId){
        LabGroup group = groupMapper.selectByPrimaryKey(groupId);
        User user = mHolder.getUser();
        if (group==null || group.getOwner() != user.getId() ){
            return ServerResponse.createByErrorMessage("缺乏权限");
        }
        group.setPublish(1);
        group.setPublishTime(new Date());
        groupMapper.updateByPrimaryKey(group);
        return ServerResponse.createBySuccessMessage("申请成功，我们会尽快审核上线");
    }

    public ServerResponse uploadClassifier(MultipartFile uploadFile,MultipartFile testFile, Map<String,Object> params) throws IOException {
//        File cFile = new File(Const.UPDATE_CLASS_ROOT_CLA,classifierFile.getOriginalFilename());
//        classifierFile.transferTo(cFile);
//        Classifier classifier = new Classifier();
//        classifier.setDes(param.get("des").toString());
//        classifier.setName(param.get("name").toString());
//        classifier.setLabId(mHolder.getUser().getId());
//        classifier.setPath(cFile.getAbsolutePath());
//        mClassifierMapper.insert(classifier);
//        for (String key:param.keySet()){
//            if (key.startsWith("paramName")){
//                if (StringUtils.isNotBlank(param.get(key).toString())){
//                    ClassifierParam classifierParam = new ClassifierParam();
//                    classifierParam.setClassifierId(classifier.getId());
//                    classifierParam.setParamName(param.get(key).toString());
//                    classifierParam.setParamDes(param.get(key.replace("Name","Des")).toString());
//                    classifierParam.setDefaultValue(param.get(key.replace("Name","Value")).toString());
//                    mClassifierParamMapper.insert(classifierParam);
//                }
//            }
//        }
//        File test = new File(Const.UPLOAD_CLASS_TEST_ROOT_CLA,testFile.getOriginalFilename());
//        testFile.transferTo(test);
//        List<File> dividerFiles = divideFile(test,0.8f);
//        StringBuilder sb = new StringBuilder("python ");
//        sb.append(cFile.getAbsolutePath()).append(" ");
//        sb.append("test=").append(dividerFiles.get(0).getAbsolutePath()).append(" ");
//        sb.append("train=").append(dividerFiles.get(1).getAbsolutePath());
//        logger.info(sb.toString());
//        String res = PythonUtils.execPy(sb.toString());
//        System.out.println(res);
//        try{
//            Result result = mGson.fromJson(res,Result.class);
//            mJedisAdapter.set(RedisKeyUtil.getUpClassKey(classifier.getId()),res);
//            return ServerResponse.createBySuccess();
//        }catch (Exception e) {
//            return ServerResponse.createBySuccessMessage(res);
//        }
        //保存算法文件
        String uploadFileName=uploadFile.getOriginalFilename();
        String newFileName=getFileName("cla",uploadFileName);
        File claFile = new File(Const.UPDATE_CLASS_ROOT_CLA,newFileName);
        uploadFile.transferTo(claFile);

        //保存测试数据
        String testFileName=testFile.getOriginalFilename();
        String newTestName=getFileName("cla",testFileName);
        File test = new File(Const.UPLOAD_CLASS_TEST_ROOT_CLA,newTestName);
        testFile.transferTo(test);


        List<File> dividerFiles = divideFile(test,0.8f);
        StringBuilder sb = new StringBuilder("python ");
        sb.append(claFile.getAbsolutePath()).append(" ");
        sb.append("test=").append(dividerFiles.get(0).getAbsolutePath()).append(" ");
        sb.append("train=").append(dividerFiles.get(1).getAbsolutePath());

        logger.info("开始测试上传的分类算法，python语句为："+sb.toString());
        String res = PythonUtils.execPy(sb.toString());
        logger.info("得到结果："+res);

        Result result;
        try{
            result=mGson.fromJson(res,Result.class);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("算法验证失败");
        }
        if(result==null){
            return ServerResponse.createByErrorMessage("算法验证失败");
        }

        UploadAlgo uploadAlgo=new UploadAlgo();
        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());
        uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_CLA);


        Classifier classifier = new Classifier();
        classifier.setDes(params.get("des").toString());
        classifier.setName(params.get("name").toString());
        classifier.setLabId(-1);
        classifier.setPath(claFile.getAbsolutePath());
        int count = mClassifierMapper.insert(classifier);

        if(count<=0){
            return ServerResponse.createByErrorMessage("分类算法保存错误!");
        }

        for (String key:params.keySet()){
            if (key.startsWith("paramName")){
                if (StringUtils.isNotBlank(params.get(key).toString())){
                    ClassifierParam classifierParam = new ClassifierParam();
                    classifierParam.setClassifierId(classifier.getId());
                    classifierParam.setParamName(params.get(key).toString());
                    classifierParam.setParamDes(params.get(key.replace("Name","Des")).toString());
                    classifierParam.setDefaultValue(params.get(key.replace("Name","Value")).toString());
                    mClassifierParamMapper.insert(classifierParam);
                }
            }
        }

        uploadAlgo.setAlgoId(classifier.getId());
        uploadAlgo.setUserId(mHolder.getUser().getId());
        uploadAlgo.setTestFile(test.getAbsolutePath());
        uploadAlgo.setAlgoAddress(claFile.getAbsolutePath());
        int insert = uploadAlgoMapper.insert(uploadAlgo);
        if(insert<=0){
            return ServerResponse.createByErrorMessage("分类算法保存上传信息错误!");
        }
        return ServerResponse.createBySuccess();

    }
    public ServerResponse uploadPre(MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params) throws IOException {
        //保存算法文件
        String uploadFileName=uploadFile.getOriginalFilename();
        String newFileName=getFileName("pre",uploadFileName);
        File preFile = new File(Const.UPDATE_CLASS_ROOT_PRE,newFileName);
        uploadFile.transferTo(preFile);

        //保存测试数据
        String testFileName=testFile.getOriginalFilename();
        String newTestName=getFileName("pre",testFileName);
        File test = new File(Const.UPLOAD_CLASS_TEST_ROOT_PRE,newTestName);
        testFile.transferTo(test);

        String path=test.getAbsolutePath();
        String opath=Const.UPLOAD_CLASS_TEST_ROOT_PRE_OPATH+"out_"+test.getName();
        StringBuilder sb = new StringBuilder("python ");
        sb.append(preFile.getAbsolutePath()).append(" ");
        sb.append("path=").append(path).append(" ");
        sb.append("opath=").append(opath);

        logger.info("开始测试上传的数据预处理算法，python语句为："+sb.toString());

        PythonUtils.execPy(sb.toString());

        File opathFile=new File(opath);
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("算法验证失败");
        }

        UploadAlgo uploadAlgo=new UploadAlgo();
        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());
        uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_PRE);

        Feature feature=new Feature();
        feature.setName(params.get("name").toString());
        feature.setDes(params.get("des").toString());
        feature.setLabel(preFile.getName());
        feature.setFeatureType(-3); //待审核的预处理算法

        int count = featureMapper.insert(feature);
        if(count<=0){
            return ServerResponse.createByErrorMessage("预处理算法保存错误!");
        }

        for (String key:params.keySet()){
            if (key.startsWith("paramName")){
                if (StringUtils.isNotBlank(params.get(key).toString())){
                    FeatureParam featureParam = new FeatureParam();
                    featureParam.setFeatureId(feature.getId());
                    featureParam.setShell(params.get(key).toString());
                    featureParam.setName(params.get(key).toString());
                    featureParam.setDes(params.get(key.replace("Name","Des")).toString());
                    featureParam.setDefaultValue(params.get(key.replace("Name","Value")).toString());
                    mFeatureParamMapper.insert(featureParam);
                }
            }
        }

        uploadAlgo.setAlgoId(feature.getId());
        uploadAlgo.setUserId(mHolder.getUser().getId());
        uploadAlgo.setTestFile(test.getAbsolutePath());
        uploadAlgo.setAlgoAddress(preFile.getAbsolutePath());
        int insert = uploadAlgoMapper.insert(uploadAlgo);
        if(insert<=0){
            return ServerResponse.createByErrorMessage("预处理上传保存错误!");
        }
        return ServerResponse.createBySuccess();
    }
    public ServerResponse uploadFea(MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params) throws IOException {
        //保存算法文件
        String uploadFileName=uploadFile.getOriginalFilename();
        String newFileName=getFileName("fea",uploadFileName);
        File feaFile = new File(Const.UPDATE_CLASS_ROOT_FEA,newFileName);
        uploadFile.transferTo(feaFile);

        //保存测试数据
        String testFileName=testFile.getOriginalFilename();
        String newTestName=getFileName("fea",testFileName);
        File test = new File(Const.UPLOAD_CLASS_TEST_ROOT_FEA,newTestName);
        testFile.transferTo(test);

        String path=test.getAbsolutePath();
        String opath=Const.UPLOAD_CLASS_TEST_ROOT_FEA_OPATH+"out_"+test.getName();
        StringBuilder sb = new StringBuilder("python ");
        sb.append(feaFile.getAbsolutePath()).append(" ");
        sb.append("path=").append(path).append(" ");
        sb.append("opath=").append(opath);

        logger.info("开始测试上传的数据特征提取算法，python语句为："+sb.toString());

        PythonUtils.execPy(sb.toString());

        File opathFile=new File(opath);
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("算法验证失败");
        }

        UploadAlgo uploadAlgo=new UploadAlgo();
        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());
        uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_FEA);

        Feature feature=new Feature();
        feature.setName(params.get("name").toString());
        feature.setDes(params.get("des").toString());
        feature.setLabel(feaFile.getName());
        feature.setFeatureType(-2); //待审核的特征提取算法

        int count = featureMapper.insert(feature);
        if(count<=0){
            return ServerResponse.createByErrorMessage("特征提取算法保存错误!");
        }

        for (String key:params.keySet()){
            if (key.startsWith("paramName")){
                if (StringUtils.isNotBlank(params.get(key).toString())){
                    FeatureParam featureParam = new FeatureParam();
                    featureParam.setFeatureId(feature.getId());
                    featureParam.setShell(params.get(key).toString());
                    featureParam.setName(params.get(key).toString());
                    featureParam.setDes(params.get(key.replace("Name","Des")).toString());
                    featureParam.setDefaultValue(params.get(key.replace("Name","Value")).toString());
                    mFeatureParamMapper.insert(featureParam);
                }
            }
        }

        uploadAlgo.setAlgoId(feature.getId());
        uploadAlgo.setUserId(mHolder.getUser().getId());
        uploadAlgo.setTestFile(test.getAbsolutePath());
        uploadAlgo.setAlgoAddress(feaFile.getAbsolutePath());
        int insert = uploadAlgoMapper.insert(uploadAlgo);
        if(insert<=0){
            return ServerResponse.createByErrorMessage("预处理上传保存错误!");
        }
        return ServerResponse.createBySuccess();
    }
    public ServerResponse uploadRegressor(MultipartFile uploadFile, MultipartFile testFile, Map<String, Object> params) throws IOException {
        String uploadFileName=uploadFile.getOriginalFilename();
        String newFileName=getFileName("reg",uploadFileName);
        File regFile = new File(Const.UPDATE_CLASS_ROOT_REG,newFileName);
        uploadFile.transferTo(regFile);

        //保存测试数据
        String testFileName=testFile.getOriginalFilename();
        String newTestName=getFileName("reg",testFileName);
        File test = new File(Const.UPLOAD_CLASS_TEST_ROOT_REG,newTestName);
        testFile.transferTo(test);


        List<File> dividerFiles = divideFile(test,0.8f);
        StringBuilder sb = new StringBuilder("python ");
        sb.append(regFile.getAbsolutePath()).append(" ");
        sb.append("test=").append(dividerFiles.get(0).getAbsolutePath()).append(" ");
        sb.append("train=").append(dividerFiles.get(1).getAbsolutePath());

        logger.info("开始测试上传的分类算法，python语句为："+sb.toString());
        String res = PythonUtils.execPy(sb.toString());
        logger.info("得到结果："+res);

        RegResult result;
        try{
            result=mGson.fromJson(res,RegResult.class);
        }catch (Exception e){
            e.printStackTrace();
            return ServerResponse.createByErrorMessage("算法验证失败");
        }
        if(result==null){
            return ServerResponse.createByErrorMessage("算法验证失败");
        }

        UploadAlgo uploadAlgo=new UploadAlgo();
        uploadAlgo.setAlgoName(params.get("name").toString());
        uploadAlgo.setAlgoDes(params.get("des").toString());
        uploadAlgo.setAlgoType(Const.UPLOAD_ALGO_TYPE_REG);


        Classifier classifier = new Classifier();
        classifier.setDes(params.get("des").toString());
        classifier.setName(params.get("name").toString());
        classifier.setLabId(-2);
        classifier.setPath(regFile.getAbsolutePath());
        int count = mClassifierMapper.insert(classifier);

        if(count<=0){
            return ServerResponse.createByErrorMessage("回归算法保存错误!");
        }

        for (String key:params.keySet()){
            if (key.startsWith("paramName")){
                if (StringUtils.isNotBlank(params.get(key).toString())){
                    ClassifierParam classifierParam = new ClassifierParam();
                    classifierParam.setClassifierId(classifier.getId());
                    classifierParam.setParamName(params.get(key).toString());
                    classifierParam.setParamDes(params.get(key.replace("Name","Des")).toString());
                    classifierParam.setDefaultValue(params.get(key.replace("Name","Value")).toString());
                    mClassifierParamMapper.insert(classifierParam);
                }
            }
        }

        uploadAlgo.setAlgoId(classifier.getId());
        uploadAlgo.setUserId(mHolder.getUser().getId());
        uploadAlgo.setTestFile(test.getAbsolutePath());
        uploadAlgo.setAlgoAddress(regFile.getAbsolutePath());
        int insert = uploadAlgoMapper.insert(uploadAlgo);
        if(insert<=0){
            return ServerResponse.createByErrorMessage("回归算法保存上传信息错误!");
        }
        return ServerResponse.createBySuccess();
    }

    private List<File> divideFile(File source,float radio) throws IOException {
        File testFile = new File(source.getParent(),source.getName().replace(".csv","test.csv"));
        File trainFile = new File(source.getParent(),source.getName().replace(".csv","train.csv"));
        CSVLoader csvLoader = new CSVLoader();
        csvLoader.setSource(source);
        Instances instances = csvLoader.getDataSet();
        Instances train = new Instances(instances,0);
        Instances test = new Instances(instances,0);
        int size = (int) (instances.size()*radio);
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        while (set.size()<size){
            set.add(random.nextInt(instances.size()));
        }
        for (int i=0;i<instances.size();i++){
            if (set.contains(i)){
                train.add(instances.get(i));
            }else {
                test.add(instances.get(i));
            }
        }

        CSVSaver saver = new CSVSaver();
        saver.setFile(trainFile);
        saver.setInstances(train);
        saver.writeBatch();

        saver.setFile(testFile);
        saver.setInstances(test);
        saver.writeBatch();
        return Arrays.asList(testFile,trainFile);
    }

    /**
     * 生成随机名字
     * @param prefix
     * @param uploadFileName
     * @return
     */

    private  String getFileName(String prefix,String uploadFileName){
        StringBuilder res=new StringBuilder();
        Random random=new Random();
        int suffIndex=uploadFileName.lastIndexOf(".");
        String suffix=uploadFileName.substring(suffIndex);
        String prefixStr=prefix+"_"+System.nanoTime()+random.nextInt(100);
        return res.append(prefixStr).append(suffix).toString();
    }

//    public static void main(String[] args) {
//////        System.out.println(getFileName("pre","smote.py"));
////
////        File file =new File("/usr/local/sk/upload/pre/1.csv");
////
////        System.out.println(file.exists());
//    }

}
