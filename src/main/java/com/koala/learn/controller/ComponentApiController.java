package com.koala.learn.controller;

import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.entity.API;
import com.koala.learn.entity.ApiAuth;
import com.koala.learn.entity.Model;
import com.koala.learn.service.AuthService;
import com.koala.learn.service.ComponentApiService;
import com.koala.learn.service.FileService;
import com.koala.learn.service.ModelService;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.divider.CsvDivider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/component")
public class ComponentApiController {

    @Autowired
    FileService fileService;

    @Autowired
    ComponentApiService componentApiService;

    @Autowired
    AuthService authService;

    @Autowired
    ModelService modelService;


    /**
     * 预处理-异常值检测并清除(IsolationForest算法)
     * @param file
     * @return
     */
    @PostMapping("/pre/iso")
    public ServerResponse preHandleIso(@RequestParam(value = "file",required = false) MultipartFile file,
                                       @RequestParam(value = "Base64_file" ,required = false) String base64Str,
                                       @RequestParam(value = "file_name" ,required = false) String fileName,
                                       @RequestParam("access_token") String accessToken,
                                       @RequestParam(value="contamination",required = false,defaultValue = "0.01") String contamination) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }

        File pathFile;
        if(fileName!=null){
            pathFile=new File(Const.UPLOAD_DATASET,fileName);
        }else{
            if(file!=null){
                ServerResponse fileResponse=fileService.checkFileSize(file);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                pathFile = fileService.addFile(file,Const.FILE_PRE_ROOT,"pre_iso");

            }else{
                pathFile = fileService.addFile(base64Str,Const.FILE_PRE_ROOT,"pre_iso");
            }
        }

        String opath= Const.FILE_OPATH_ROOT+"iso_c"+contamination+"_"+pathFile.getName();
        return componentApiService.checkPreIso(pathFile.getAbsolutePath(),opath,contamination);
    }

    /**
     * 预处理-样本均衡-过采样SMOTE算法
     * @param file
     * @param accessToken
     * @param ratio
     * @return
     * @throws IOException
     */
    @PostMapping("/pre/smote")
    public ServerResponse preHandleSMOTE(@RequestParam(value = "file",required = false) MultipartFile file,
                                         @RequestParam(value = "Base64_file" ,required = false) String base64Str,
                                         @RequestParam(value = "file_name" ,required = false) String fileName,
                                         @RequestParam("access_token") String accessToken,
                                         @RequestParam(value = "k_neighbors",required = false,defaultValue = "5") String kNeighbors,
                                         @RequestParam(value="ratio",required = false,defaultValue = "auto") String ratio) throws Exception {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        File pathFile;
        if(fileName!=null){
            pathFile=new File(Const.UPLOAD_DATASET,fileName);
        }else{
            if(file!=null){
                ServerResponse fileResponse=fileService.checkFileSize(file);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                pathFile = fileService.addFile(file,Const.FILE_PRE_ROOT,"pre_smote");

            }else{
                pathFile = fileService.addFile(base64Str,Const.FILE_PRE_ROOT,"pre_smote");
            }
        }

        String opath= Const.FILE_OPATH_ROOT+"smote_ratio"+ratio+"_k_"+kNeighbors+pathFile.getName();
        return componentApiService.execSMOTE(pathFile.getAbsolutePath(),opath,kNeighbors,ratio);
    }

    /**
     * 预处理-数据归一化
     * @param file
     * @param accessToken
     * @return
     * @throws IOException
     */
    @PostMapping("/pre/normalization")
    public ServerResponse preHandleNormalization(@RequestParam(value = "file",required = false) MultipartFile file,
                                                 @RequestParam(value = "file_name" ,required = false) String fileName,
                                                 @RequestParam(value = "Base64_file" ,required = false) String base64Str,
                                                 @RequestParam("access_token") String accessToken)
                                          throws Exception {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        File pathFile;
        if(fileName!=null){
            pathFile=new File(Const.UPLOAD_DATASET,fileName);
        }else{
            if(file!=null){
                ServerResponse fileResponse=fileService.checkFileSize(file);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                pathFile = fileService.addFile(file,Const.FILE_PRE_ROOT,"pre_nor");

            }else{
                pathFile = fileService.addFile(base64Str,Const.FILE_PRE_ROOT,"pre_nor");
            }
        }
        String opath= Const.FILE_OPATH_ROOT+"nor_"+pathFile.getName();
        return componentApiService.execNormalization(pathFile.getAbsolutePath(),opath);
    }

    /**
     * 特征处理--时间窗
     * @param fileName
     * @param accessToken
     * @param windowLength
     * @param stepLength
     * @return
     */
    @PostMapping("/feature/window")
    public ServerResponse window(@RequestParam(value = "file_name" ,required = false) String fileName,
                                 @RequestParam("access_token") String accessToken,
                                 @RequestParam(value = "window_length",defaultValue = "15",required = false)Integer windowLength,
                                 @RequestParam(value = "step_length",defaultValue = "5",required = false)Integer stepLength) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        File file=new File(Const.UPLOAD_DATASET,fileName);
        String opath= Const.FILE_OPATH_ROOT+"window_wl"+windowLength+"_sl_"+stepLength+file.getName();
        return componentApiService.execWindow(file.getAbsolutePath(),opath,windowLength,stepLength);

    }

    /**
     * 特征提取--时域
     * @param file
     * @param accessToken
     * @return
     * @throws IOException
     */
    @PostMapping("/feature/time")
    public ServerResponse featureTime(@RequestParam(value = "file",required = false) MultipartFile file,
                                      @RequestParam(value = "Base64_file" ,required = false) String base64Str,
                                      @RequestParam(value = "file_name" ,required = false) String fileName,
                                      @RequestParam("access_token") String accessToken,
                                      @RequestParam(value = "window_length",defaultValue = "10")Integer windowLength,
                                      @RequestParam(value = "avg",defaultValue = "0",required = false)Integer avg,
                                      @RequestParam(value = "std",defaultValue = "0",required = false)Integer std,
                                      @RequestParam(value = "var",defaultValue = "0",required = false)Integer var,
                                      @RequestParam(value = "skew",defaultValue = "0",required = false)Integer skew,
                                      @RequestParam(value = "kur",defaultValue = "0",required = false)Integer kur,
                                      @RequestParam(value = "ptp",defaultValue = "0",required = false)Integer ptp)
            throws Exception {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }

        File pathFile;

        if(fileName!=null){
            pathFile=new File(Const.UPLOAD_DATASET,fileName);
        }else{
            if(file!=null){
                ServerResponse fileResponse=fileService.checkFileSize(file);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                pathFile = fileService.addFile(file,Const.FILE_FEATURE_ROOT,"fea_time");

            }else{
                pathFile = fileService.addFile(base64Str,Const.FILE_FEATURE_ROOT,"fea_time");
            }
        }
        String opath= Const.FILE_OPATH_ROOT+"time_avg"+avg+"_std_"+std+"_var_"+var+"_skew_"+skew+"_kur_"+kur+"_ptp_"+ptp+pathFile.getName();
        return componentApiService.execFeatureTime(pathFile.getAbsolutePath(),opath,windowLength,avg,std,var,skew,kur,ptp);
    }

    /**
     * 特征提取--频域
     * @param file
     * @param accessToken
     * @return
     * @throws IOException
     */
    @PostMapping("/feature/freq")
    public ServerResponse featureFreq(@RequestParam(value = "file",required = false) MultipartFile file,
                                      @RequestParam(value = "Base64_file" ,required = false) String base64Str,
                                      @RequestParam(value = "file_name" ,required = false) String fileName,
                                      @RequestParam("access_token") String accessToken,
                                      @RequestParam(value = "window_length",defaultValue = "10")Integer windowLength,
                                      @RequestParam(value = "min_fre")Integer minFre,
                                      @RequestParam(value = "max_fre")Integer maxFre,
                                      @RequestParam(value = "freq")Integer freq)
            throws Exception {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        File pathFile;
        if(fileName!=null){
            pathFile=new File(Const.UPLOAD_DATASET,fileName);
        }else{
            if(file!=null){
                ServerResponse fileResponse=fileService.checkFileSize(file);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                pathFile = fileService.addFile(file,Const.FILE_FEATURE_ROOT,"fea_freq");

            }else{
                pathFile = fileService.addFile(base64Str,Const.FILE_FEATURE_ROOT,"fea_freq");
            }
        }
        String opath= Const.FILE_OPATH_ROOT+"freq_wl"+windowLength+"_minf_"+minFre+"_maxf_"+maxFre+"_freq_"+freq+pathFile.getName();
        return componentApiService.execFeatureFreq(pathFile.getAbsolutePath(),opath,windowLength,minFre,maxFre,freq);
    }


    /**
     * 特征提取--时频域
     * @param file
     * @param accessToken
     * @return
     * @throws IOException
     */
    @PostMapping("/feature/wav")
    public ServerResponse featureFreq(@RequestParam(value = "file",required = false) MultipartFile file,
                                      @RequestParam(value = "Base64_file" ,required = false) String base64Str,
                                      @RequestParam(value = "file_name" ,required = false) String fileName,
                                      @RequestParam("access_token") String accessToken,
                                      @RequestParam(value = "window_length",defaultValue = "10")Integer windowLength,
                                      @RequestParam(value = "wave_layer",defaultValue = "3",required = false)Integer waveLayer)
            throws Exception {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        File pathFile;
        if(fileName!=null){
            pathFile=new File(Const.UPLOAD_DATASET,fileName);
        }else{
            if(file!=null){
                ServerResponse fileResponse=fileService.checkFileSize(file);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                pathFile = fileService.addFile(file,Const.FILE_FEATURE_ROOT,"fea_wav");

            }else{
                pathFile = fileService.addFile(base64Str,Const.FILE_FEATURE_ROOT,"fea_wav");
            }
        }
        String opath= Const.FILE_OPATH_ROOT+"wav_wl"+windowLength+"wave_layer"+waveLayer+pathFile.getName();
        return componentApiService.execFeatureWav(pathFile.getAbsolutePath(),opath,windowLength,waveLayer);
    }

    /**
     * 机器学习分类算法
     */

    @RequestMapping("/ML/classify/{classifierId}")
    @ResponseBody
    public ServerResponse handleClassify(@RequestParam(value = "file_name") String fileName,
                                         @RequestParam("access_token") String accessToken,
                                         @PathVariable("classifierId") Integer classifierId,
                                         @RequestParam Map<String,String> param) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }

        CsvDivider.divide(new File(Const.UPLOAD_DATASET,fileName),0.8);
        File train=new File(Const.UPLOAD_DATASET,CsvDivider.getTrainFileName(fileName,0.8));
        File test=new File(Const.UPLOAD_DATASET,CsvDivider.getTestFileName(fileName,0.8));
        return componentApiService.execClassify(train.getAbsolutePath(),test.getAbsolutePath(),param,classifierId);
    }

    /**
     * 机器学习分类算法
     */

    @RequestMapping("/ML/classify/2_file/{classifierId}")
    @ResponseBody
    public ServerResponse handleClassify2(@RequestParam(value = "train_file",required = false) MultipartFile trainFile,
                                          @RequestParam(value = "Base64_train" ,required = false) String base64Train,
                                          @RequestParam(value = "train_name" ,required = false) String trainName,
                                          @RequestParam(value = "test_name" ,required = false) String testName,
                                          @RequestParam(value = "test_file",required = false) MultipartFile testFile,
                                          @RequestParam(value = "Base64_test" ,required = false) String base64Test,
                                          @RequestParam("access_token") String accessToken,
                                          @PathVariable("classifierId") Integer classifierId,
                                          @RequestParam Map<String,String> param) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        File train;
        if(trainName!=null){
            train=new File(Const.UPLOAD_DATASET,trainName);
        }else{
            if(trainFile!=null){
                ServerResponse fileResponse=fileService.checkFileSize(trainFile);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                train = fileService.addFile(trainFile,Const.FILE_CLASSIFY_ROOT,"train");

            }else{
                train = fileService.addFile(base64Train,Const.FILE_CLASSIFY_ROOT,"train");
            }
        }

        File test;
        if(testName!=null){
            test=new File(Const.UPLOAD_DATASET,testName);
        }else{
            if(testFile!=null){
                ServerResponse fileResponse=fileService.checkFileSize(testFile);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                test = fileService.addFile(testFile,Const.FILE_CLASSIFY_ROOT,"test");

            }else{
                test = fileService.addFile(base64Test,Const.FILE_CLASSIFY_ROOT,"test");
            }
        }
        return componentApiService.execClassify(train.getAbsolutePath(),test.getAbsolutePath(),param,classifierId);
    }

    /**
     * 机器学习回归算法
     */

    @RequestMapping("/ML/regression/{classifierId}")
    @ResponseBody
    public ServerResponse handleRegression(@RequestParam(value = "file_name") String fileName,
                                           @RequestParam("access_token") String accessToken,
                                           @PathVariable("classifierId") Integer classifierId,
                                           @RequestParam Map<String,String> param) {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }

        CsvDivider.divide(new File(Const.UPLOAD_DATASET,fileName),0.8);
        File train=new File(Const.UPLOAD_DATASET,CsvDivider.getTrainFileName(fileName,0.8));
        File test=new File(Const.UPLOAD_DATASET,CsvDivider.getTestFileName(fileName,0.8));
        return componentApiService.execRegressor(train.getAbsolutePath(),test.getAbsolutePath(),param,classifierId);
    }

    /**
     * 机器学习回归算法
     */

    @RequestMapping("/ML/regression/2_file/{classifierId}")
    @ResponseBody
    public ServerResponse handleRegression2(@RequestParam(value = "train_file",required = false) MultipartFile trainFile,
                                            @RequestParam(value = "Base64_train" ,required = false) String base64Train,
                                            @RequestParam(value = "test_file",required =  false) MultipartFile testFile,
                                            @RequestParam(value = "train_name" ,required = false) String trainName,
                                            @RequestParam(value = "test_name" ,required = false) String testName,
                                            @RequestParam(value = "Base64_test" ,required = false) String base64Test,
                                            @RequestParam("access_token") String accessToken,
                                            @PathVariable("classifierId") Integer classifierId,
                                            @RequestParam Map<String,String> param) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        File train;
        if(trainName!=null){
            train=new File(Const.UPLOAD_DATASET,trainName);
        }else{
            if(trainFile!=null){
                ServerResponse fileResponse=fileService.checkFileSize(trainFile);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                train = fileService.addFile(trainFile,Const.FILE_REGRESSION_ROOT,"train");

            }else{
                train = fileService.addFile(base64Train,Const.FILE_REGRESSION_ROOT,"train");
            }
        }
        File test;
        if(testName!=null){
            test=new File(Const.UPLOAD_DATASET,testName);
        }else{
            if(testFile!=null){
                ServerResponse fileResponse=fileService.checkFileSize(testFile);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                test = fileService.addFile(testFile,Const.FILE_REGRESSION_ROOT,"test");

            }else{
                test = fileService.addFile(base64Test,Const.FILE_REGRESSION_ROOT,"test");
            }
        }
        return componentApiService.execRegressor(train.getAbsolutePath(),test.getAbsolutePath(),param,classifierId);
    }

//    /**
//     * 预处理-异常值检测并清除(IsolationForest算法)Base64传输
//     * @param base64Str
//     * @return
//     */
//    @PostMapping("/pre/iso/base64")
//    public ServerResponse preHandleIsoBase64(@RequestParam("base64Str") String base64Str,
//                                       @RequestParam("access_token") String accessToken,
//                                       @RequestParam(value="contamination",required = false,defaultValue = "0.01") String contamination) throws IOException {
//        ServerResponse response=authService.checkAccessToken(accessToken);
//        if(!response.isSuccess()){
//            return response;
//        }
//        File pathFile = fileService.addFile(base64Str,Const.FILE_FEATURE_ROOT,"fea_freq");
//        String opath= Const.FILE_OPATH_ROOT+"out_"+pathFile.getName();
//        return componentApiService.checkPreIso(pathFile.getAbsolutePath(),opath,contamination);
//    }


    /**
     * 上传的算法生成的api
     * @param file
     * @param base64Str
     * @param accessToken
     * @param params
     * @return
     */
    @PostMapping("/upload/{apiType}/{apiId}")
    public ServerResponse uploadApi(@RequestParam(value = "file",required = false) MultipartFile file,
                                    @RequestParam(value = "Base64_file" ,required = false) String base64Str,
                                    @RequestParam(value = "file_name" ,required = false) String fileName,
                                    @RequestParam("access_token") String accessToken,
                                    @RequestParam Map<String,Object> params,
                                    @PathVariable("apiType") Integer apiType,
                                    @PathVariable("apiId") Integer apiId) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }

        API api =componentApiService.getAPIById(apiId);
        if(api.getPub().equals(0)){
            String apikey=accessToken.split("\\.")[0];
            ApiAuth apiAuth=authService.getApiAuthByApiKey(apikey);
            if(!apiAuth.getUserId().equals(api.getUserId())){
                return ServerResponse.createByErrorMessage("该api是私有的，您无使用权限");
            }
        }
        File pathFile;
        if(fileName!=null){
            pathFile=new File(Const.UPLOAD_DATASET,fileName);
        }else{
            if(file!=null && base64Str==null){
                ServerResponse fileResponse=fileService.checkFileSize(file);
                if(!fileResponse.isSuccess()){
                    return fileResponse;
                }
                if(apiType.equals(1)){
                    pathFile = fileService.addFile(file,Const.FILE_PRE_ROOT,"upload_pre");
                }else{
                    pathFile = fileService.addFile(file,Const.FILE_FEATURE_ROOT,"upload_fea");
                }

            }else{
                if(apiType.equals(1)){
                    pathFile = fileService.addFile(base64Str,Const.FILE_PRE_ROOT,"upload_pre");

                }else{
                    pathFile = fileService.addFile(base64Str,Const.FILE_FEATURE_ROOT,"upload_fea");

                }
            }
        }
        String opath= Const.FILE_OPATH_ROOT+"out_"+pathFile.getName();
        return componentApiService.execUploadPreAndFea(pathFile.getAbsolutePath(),opath,params,apiId);
    }

    /**
     * 上传的算法生成的api
     * @param accessToken
     * @param params
     * @return
     */
    @PostMapping("/upload/ML/{apiType}/{uploadAlgoId}")
    public ServerResponse uploadMLApi(@RequestParam(value = "file_name") String fileName,
                                      @RequestParam("access_token") String accessToken,
                                      @RequestParam Map<String,Object> params,
                                      @PathVariable("apiType") Integer apiType,
                                      @PathVariable("uploadAlgoId") Integer uploadAlgoId) throws IOException {

        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }

        API api =componentApiService.getAPIByUploadAlgoId(uploadAlgoId);
        if(api.getPub().equals(0)){
            String apikey=accessToken.split("\\.")[0];
            ApiAuth apiAuth=authService.getApiAuthByApiKey(apikey);
            if(!apiAuth.getUserId().equals(api.getUserId())){
                return ServerResponse.createByErrorMessage("该api是私有的，您无使用权限");
            }
        }

        CsvDivider.divide(new File(Const.UPLOAD_DATASET,fileName),0.8);
        File train=new File(Const.UPLOAD_DATASET,CsvDivider.getTrainFileName(fileName,0.8));
        File test=new File(Const.UPLOAD_DATASET,CsvDivider.getTestFileName(fileName,0.8));

        return componentApiService.execUploadML(train.getAbsolutePath(),test.getAbsolutePath(),params,api.getUploadAlgoId(),apiType);
    }

    @PostMapping("/upload/ML/model/{modelId}/{uploadAlgoId}")
    public ServerResponse uploadMLModelApi(@RequestParam(value = "file_name") String fileName,
                                      @RequestParam("access_token") String accessToken,
                                      @PathVariable("modelId") Integer modelId,
                                      @PathVariable("uploadAlgoId") Integer uploadAlgoId) throws IOException {

        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }

        Model model=modelService.getModelById(modelId);
        return componentApiService.execUploadModel(fileName,uploadAlgoId,model);
    }

    /**
     * 获取风机数据
     */

    @PostMapping("/data/fengji")
    public  ServerResponse getData( @RequestParam("access_token") String accessToken,
                                    @RequestParam("divice_id") Integer diviceId,
                                    @RequestParam("group_id")String groupIds,
                                    @RequestParam("atrribute") String atrributeName) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        return componentApiService.execFjData(diviceId,groupIds,atrributeName);
    }

    /**
     * 获取轴承数据
     */

    @PostMapping("/data/zhoucheng")
    public  ServerResponse getData( @RequestParam("access_token") String accessToken,
                                    @RequestParam("divice_id") Integer diviceId,
                                    @RequestParam("atrribute") String atrributeName) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        return componentApiService.execZcData(diviceId,atrributeName);
    }

    /**
     * 获取原始数据
     */

    @PostMapping("/data/{data_id}")
    public  ServerResponse getData2( @RequestParam("access_token") String accessToken,
                                     @PathVariable("data_id")Integer dataId,
                                    @RequestParam("divice_id") Integer diviceId,
                                    @RequestParam("atrribute") String atrributeName) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        return componentApiService.getData(dataId,diviceId,atrributeName);
    }

    @PostMapping("/ML/predict/{classifierId}")
    public  ServerResponse predict( @RequestParam("access_token") String accessToken,
                                    @RequestParam(value = "file_name" ,required = false)String fileName,
                                    @RequestParam Map<String,Object> params,
                                    @PathVariable("classifierId") Integer classifierId) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        return componentApiService.execPredict(fileName,params,classifierId);
    }

    @PostMapping("/feature/zhoucheng")
    public  ServerResponse predict( @RequestParam("access_token") String accessToken,
                                    @RequestParam(value = "file_name" ,required = false)String fileName) throws IOException {
        ServerResponse response=authService.checkAccessToken(accessToken);
        if(!response.isSuccess()){
            return response;
        }
        String opath=Const.FILE_OPATH_FEA_ZHOUCHENG+"out_"+fileName+".csv";
        File opathFile=new File(opath);
        if(opathFile.exists()){
            Map<String,Object> map=new HashMap<>();
            map.put("file_name",opathFile.getName());
            return ServerResponse.createBySuccess(map);
        }

        StringBuilder sb = new StringBuilder("python ");
        sb.append(Const.FILE_FEA_ZHOUCHENG).append(" ");
        sb.append("path=").append(Const.FILE_OPATH_FEA_ZHOUCHENG+fileName).append(" ");
        sb.append("opath=").append(opath);

        System.out.println(sb.toString());
        PythonUtils.execPy(sb.toString());
        if(!opathFile.exists() || opathFile.length()<=0){
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }
        Map<String,Object> map=new HashMap<>();
        map.put("file_name",opathFile.getName());
        return ServerResponse.createBySuccess(map);
    }
}
