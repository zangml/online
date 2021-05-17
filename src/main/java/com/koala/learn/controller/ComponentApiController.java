package com.koala.learn.controller;

import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.dao.APIMapper;
import com.koala.learn.dao.DatasetMapper;
import com.koala.learn.dao.UserRecordMapper;
import com.koala.learn.entity.*;
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
import java.util.Date;
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

    @Autowired
    APIMapper apiMapper;

    @Autowired
    UserRecordMapper userRecordMapper;

    @Autowired
    DatasetMapper datasetMapper;


    /**
     * 预处理-异常值检测并清除(IsolationForest算法)
     *
     * @param file
     * @return
     */
    @PostMapping("/pre/iso")
    public ServerResponse preHandleIso(@RequestParam(value = "file", required = false) MultipartFile file,
                                       @RequestParam(value = "Base64_file", required = false) String base64Str,
                                       @RequestParam(value = "file_name", required = false) String fileName,
                                       @RequestParam("access_token") String accessToken,
                                       @RequestParam(value = "contamination", required = false, defaultValue = "0.01") String contamination) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = apiMapper.selectById(4);
        api.setUsedCount(api.getUsedCount() + 1);
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(api.getId());


        File pathFile;
        if (fileName != null) {
            pathFile = new File(Const.UPLOAD_DATASET, fileName);
        } else {
            if (file != null) {
                ServerResponse fileResponse = fileService.checkFileSize(file);
                if (!fileResponse.isSuccess()) {
                    return fileResponse;
                }
                pathFile = fileService.addFile(file, Const.FILE_PRE_ROOT, "pre_iso");

            } else {
                pathFile = fileService.addFile(base64Str, Const.FILE_PRE_ROOT, "pre_iso");
            }
        }

        String opath = Const.FILE_OPATH_ROOT + "iso_c" + contamination + "_" + pathFile.getName();
        ServerResponse serverResponse = componentApiService.checkPreIso(pathFile.getAbsolutePath(), opath, contamination);
        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     * 预处理-样本均衡-过采样SMOTE算法
     *
     * @param file
     * @param accessToken
     * @param ratio
     * @return
     * @throws IOException
     */
    @PostMapping("/pre/smote")
    public ServerResponse preHandleSMOTE(@RequestParam(value = "file", required = false) MultipartFile file,
                                         @RequestParam(value = "Base64_file", required = false) String base64Str,
                                         @RequestParam(value = "file_name", required = false) String fileName,
                                         @RequestParam("access_token") String accessToken,
                                         @RequestParam(value = "k_neighbors", required = false, defaultValue = "5") String kNeighbors,
                                         @RequestParam(value = "ratio", required = false, defaultValue = "auto") String ratio) throws Exception {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = apiMapper.selectById(3);
        api.setUsedCount(api.getUsedCount() + 1);
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(api.getId());

        File pathFile;
        if (fileName != null) {
            pathFile = new File(Const.UPLOAD_DATASET, fileName);
        } else {
            if (file != null) {
                ServerResponse fileResponse = fileService.checkFileSize(file);
                if (!fileResponse.isSuccess()) {
                    return fileResponse;
                }
                pathFile = fileService.addFile(file, Const.FILE_PRE_ROOT, "pre_smote");

            } else {
                pathFile = fileService.addFile(base64Str, Const.FILE_PRE_ROOT, "pre_smote");
            }
        }

        String opath = Const.FILE_OPATH_ROOT + "smote_ratio" + ratio + "_k_" + kNeighbors + pathFile.getName();
        ServerResponse serverResponse = componentApiService.execSMOTE(pathFile.getAbsolutePath(), opath, kNeighbors, ratio);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     * 预处理-数据归一化
     *
     * @param file
     * @param accessToken
     * @return
     * @throws IOException
     */
    @PostMapping("/pre/normalization")
    public ServerResponse preHandleNormalization(@RequestParam(value = "file", required = false) MultipartFile file,
                                                 @RequestParam(value = "file_name", required = false) String fileName,
                                                 @RequestParam(value = "Base64_file", required = false) String base64Str,
                                                 @RequestParam("access_token") String accessToken)
            throws Exception {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = apiMapper.selectById(5);
        api.setUsedCount(api.getUsedCount() + 1);
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(api.getId());

        File pathFile;
        if (fileName != null) {
            pathFile = new File(Const.UPLOAD_DATASET, fileName);
        } else {
            if (file != null) {
                ServerResponse fileResponse = fileService.checkFileSize(file);
                if (!fileResponse.isSuccess()) {
                    return fileResponse;
                }
                pathFile = fileService.addFile(file, Const.FILE_PRE_ROOT, "pre_nor");

            } else {
                pathFile = fileService.addFile(base64Str, Const.FILE_PRE_ROOT, "pre_nor");
            }
        }
        String opath = Const.FILE_OPATH_ROOT + "nor_" + pathFile.getName();
        ServerResponse serverResponse = componentApiService.execNormalization(pathFile.getAbsolutePath(), opath);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     * 特征处理--时间窗
     *
     * @param fileName
     * @param accessToken
     * @param windowLength
     * @param stepLength
     * @return
     */
    @PostMapping("/feature/window")
    public ServerResponse window(@RequestParam(value = "file_name", required = false) String fileName,
                                 @RequestParam("access_token") String accessToken,
                                 @RequestParam(value = "window_length", defaultValue = "15", required = false) Integer windowLength,
                                 @RequestParam(value = "step_length", defaultValue = "5", required = false) Integer stepLength) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = apiMapper.selectById(20);
        api.setUsedCount(api.getUsedCount() + 1);
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(api.getId());

        File file = new File(Const.UPLOAD_DATASET, fileName);
        String opath = Const.FILE_OPATH_ROOT + "window_wl" + windowLength + "_sl_" + stepLength + file.getName();
        ServerResponse serverResponse = componentApiService.execWindow(file.getAbsolutePath(), opath, windowLength, stepLength);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;


    }

    /**
     * 特征提取--时域
     *
     * @param file
     * @param accessToken
     * @return
     * @throws IOException
     */
    @PostMapping("/feature/time")
    public ServerResponse featureTime(@RequestParam(value = "file", required = false) MultipartFile file,
                                      @RequestParam(value = "Base64_file", required = false) String base64Str,
                                      @RequestParam(value = "file_name", required = false) String fileName,
                                      @RequestParam("access_token") String accessToken,
                                      @RequestParam(value = "window_length", defaultValue = "10") Integer windowLength,
                                      @RequestParam(value = "avg", defaultValue = "0", required = false) Integer avg,
                                      @RequestParam(value = "std", defaultValue = "0", required = false) Integer std,
                                      @RequestParam(value = "var", defaultValue = "0", required = false) Integer var,
                                      @RequestParam(value = "skew", defaultValue = "0", required = false) Integer skew,
                                      @RequestParam(value = "kur", defaultValue = "0", required = false) Integer kur,
                                      @RequestParam(value = "ptp", defaultValue = "0", required = false) Integer ptp)
            throws Exception {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = apiMapper.selectById(6);
        api.setUsedCount(api.getUsedCount() + 1);
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(api.getId());

        File pathFile;

        if (fileName != null) {
            pathFile = new File(Const.UPLOAD_DATASET, fileName);
        } else {
            if (file != null) {
                ServerResponse fileResponse = fileService.checkFileSize(file);
                if (!fileResponse.isSuccess()) {
                    return fileResponse;
                }
                pathFile = fileService.addFile(file, Const.FILE_FEATURE_ROOT, "fea_time");

            } else {
                pathFile = fileService.addFile(base64Str, Const.FILE_FEATURE_ROOT, "fea_time");
            }
        }
        String opath = Const.FILE_OPATH_ROOT + "time_avg" + avg + "_std_" + std + "_var_" + var + "_skew_" + skew + "_kur_" + kur + "_ptp_" + ptp + pathFile.getName();
        ServerResponse serverResponse = componentApiService.execFeatureTime(pathFile.getAbsolutePath(), opath, windowLength, avg, std, var, skew, kur, ptp);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     * 特征提取--频域
     *
     * @param file
     * @param accessToken
     * @return
     * @throws IOException
     */
    @PostMapping("/feature/freq")
    public ServerResponse featureFreq(@RequestParam(value = "file", required = false) MultipartFile file,
                                      @RequestParam(value = "Base64_file", required = false) String base64Str,
                                      @RequestParam(value = "file_name", required = false) String fileName,
                                      @RequestParam("access_token") String accessToken,
                                      @RequestParam(value = "window_length", defaultValue = "10") Integer windowLength,
                                      @RequestParam(value = "min_fre") Integer minFre,
                                      @RequestParam(value = "max_fre") Integer maxFre,
                                      @RequestParam(value = "freq") Integer freq)
            throws Exception {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = apiMapper.selectById(7);
        api.setUsedCount(api.getUsedCount() + 1);
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(api.getId());

        File pathFile;
        if (fileName != null) {
            pathFile = new File(Const.UPLOAD_DATASET, fileName);
        } else {
            if (file != null) {
                ServerResponse fileResponse = fileService.checkFileSize(file);
                if (!fileResponse.isSuccess()) {
                    return fileResponse;
                }
                pathFile = fileService.addFile(file, Const.FILE_FEATURE_ROOT, "fea_freq");

            } else {
                pathFile = fileService.addFile(base64Str, Const.FILE_FEATURE_ROOT, "fea_freq");
            }
        }
        String opath = Const.FILE_OPATH_ROOT + "freq_wl" + windowLength + "_minf_" + minFre + "_maxf_" + maxFre + "_freq_" + freq + pathFile.getName();
        ServerResponse serverResponse = componentApiService.execFeatureFreq(pathFile.getAbsolutePath(), opath, windowLength, minFre, maxFre, freq);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }


    /**
     * 特征提取--时频域
     *
     * @param file
     * @param accessToken
     * @return
     * @throws IOException
     */
    @PostMapping("/feature/wav")
    public ServerResponse featureFreq(@RequestParam(value = "file", required = false) MultipartFile file,
                                      @RequestParam(value = "Base64_file", required = false) String base64Str,
                                      @RequestParam(value = "file_name", required = false) String fileName,
                                      @RequestParam("access_token") String accessToken,
                                      @RequestParam(value = "window_length", defaultValue = "10") Integer windowLength,
                                      @RequestParam(value = "wave_layer", defaultValue = "3", required = false) Integer waveLayer)
            throws Exception {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = apiMapper.selectById(8);
        api.setUsedCount(api.getUsedCount() + 1);
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(api.getId());

        File pathFile;
        if (fileName != null) {
            pathFile = new File(Const.UPLOAD_DATASET, fileName);
        } else {
            if (file != null) {
                ServerResponse fileResponse = fileService.checkFileSize(file);
                if (!fileResponse.isSuccess()) {
                    return fileResponse;
                }
                pathFile = fileService.addFile(file, Const.FILE_FEATURE_ROOT, "fea_wav");

            } else {
                pathFile = fileService.addFile(base64Str, Const.FILE_FEATURE_ROOT, "fea_wav");
            }
        }
        String opath = Const.FILE_OPATH_ROOT + "wav_wl" + windowLength + "wave_layer" + waveLayer + pathFile.getName();
        ServerResponse serverResponse = componentApiService.execFeatureWav(pathFile.getAbsolutePath(), opath, windowLength, waveLayer);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     * 机器学习分类算法
     */

    @RequestMapping("/ML/classify/{classifierId}")
    @ResponseBody
    public ServerResponse handleClassify(@RequestParam(value = "file_name") String fileName,
                                         @RequestParam("access_token") String accessToken,
                                         @PathVariable("classifierId") Integer classifierId,
                                         @RequestParam Map<String, String> param) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        int apiId = 0;
        if (classifierId == 7) {
            API api = apiMapper.selectById(10);
            api.setUsedCount(api.getUsedCount() + 1);
            apiMapper.update(api);
            apiId = api.getId();

        }
        if (classifierId == 38) {
            API api = apiMapper.selectById(11);
            api.setUsedCount(api.getUsedCount() + 1);
            apiMapper.update(api);
            apiId = api.getId();
        }
        if (classifierId == 13) {
            API api = apiMapper.selectById(12);
            api.setUsedCount(api.getUsedCount() + 1);
            apiMapper.update(api);
            apiId = api.getId();
        }

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(apiId);


        CsvDivider.divide(new File(Const.UPLOAD_DATASET, fileName), 0.8);
        File train = new File(Const.UPLOAD_DATASET, CsvDivider.getTrainFileName(fileName, 0.8));
        File test = new File(Const.UPLOAD_DATASET, CsvDivider.getTestFileName(fileName, 0.8));
        ServerResponse serverResponse = componentApiService.execClassify(train.getAbsolutePath(), test.getAbsolutePath(), param, classifierId);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     * 机器学习分类算法
     */

//    @RequestMapping("/ML/classify/2_file/{classifierId}")
//    @ResponseBody
//    public ServerResponse handleClassify2(@RequestParam(value = "train_file", required = false) MultipartFile trainFile,
//                                          @RequestParam(value = "Base64_train", required = false) String base64Train,
//                                          @RequestParam(value = "train_name", required = false) String trainName,
//                                          @RequestParam(value = "test_name", required = false) String testName,
//                                          @RequestParam(value = "test_file", required = false) MultipartFile testFile,
//                                          @RequestParam(value = "Base64_test", required = false) String base64Test,
//                                          @RequestParam("access_token") String accessToken,
//                                          @PathVariable("classifierId") Integer classifierId,
//                                          @RequestParam Map<String, String> param) throws IOException {
//        ServerResponse response = authService.checkAccessToken(accessToken);
//        if (!response.isSuccess()) {
//            return response;
//        }
//
//        int apiId = 0;
//        if (classifierId == 7) {
//            API api = apiMapper.selectById(10);
//            api.setUsedCount(api.getUsedCount() + 1);
//            apiMapper.update(api);
//            apiId = api.getId();
//        }
//        if (classifierId == 38) {
//            API api = apiMapper.selectById(11);
//            api.setUsedCount(api.getUsedCount() + 1);
//            apiMapper.update(api);
//            apiId = api.getId();
//        }
//        if (classifierId == 13) {
//            API api = apiMapper.selectById(12);
//            api.setUsedCount(api.getUsedCount() + 1);
//            apiMapper.update(api);
//            apiId = api.getId();
//        }
//        if (classifierId == 48) {
//            API api = apiMapper.selectById(13);
//            api.setUsedCount(api.getUsedCount() + 1);
//            apiMapper.update(api);
//            apiId = api.getId();
//        }
//        if (classifierId == 44) {
//            API api = apiMapper.selectById(14);
//            api.setUsedCount(api.getUsedCount() + 1);
//            apiMapper.update(api);
//            apiId = api.getId();
//        }
//        if (classifierId == 43) {
//            API api = apiMapper.selectById(15);
//            api.setUsedCount(api.getUsedCount() + 1);
//            apiMapper.update(api);
//            apiId = api.getId();
//        }
//
//        UserRecord userRecord = new UserRecord();
//        userRecord.setUserId((int) response.getData());
//        userRecord.setRecordTime(new Date());
//        userRecord.setRecordType(2);
//        userRecord.setRecordTypeId(apiId);
//
//        File train;
//        if (trainName != null) {
//            train = new File(Const.UPLOAD_DATASET, trainName);
//        } else {
//            if (trainFile != null) {
//                ServerResponse fileResponse = fileService.checkFileSize(trainFile);
//                if (!fileResponse.isSuccess()) {
//                    return fileResponse;
//                }
//                train = fileService.addFile(trainFile, Const.FILE_CLASSIFY_ROOT, "train");
//
//            } else {
//                train = fileService.addFile(base64Train, Const.FILE_CLASSIFY_ROOT, "train");
//            }
//        }
//
//        File test;
//        if (testName != null) {
//            test = new File(Const.UPLOAD_DATASET, testName);
//        } else {
//            if (testFile != null) {
//                ServerResponse fileResponse = fileService.checkFileSize(testFile);
//                if (!fileResponse.isSuccess()) {
//                    return fileResponse;
//                }
//                test = fileService.addFile(testFile, Const.FILE_CLASSIFY_ROOT, "test");
//
//            } else {
//                test = fileService.addFile(base64Test, Const.FILE_CLASSIFY_ROOT, "test");
//            }
//        }
//        ServerResponse serverResponse = componentApiService.execClassify(train.getAbsolutePath(), test.getAbsolutePath(), param, classifierId);
//
//        if(serverResponse.isSuccess()){
//            userRecord.setState(0);
//        }else{
//            userRecord.setState(1);
//            userRecord.setMsg(serverResponse.getMsg());
//        }
//        userRecordMapper.insert(userRecord);
//
//        return serverResponse;
//    }

    /**
     * 机器学习回归算法
     */

    @RequestMapping("/ML/regression/{classifierId}")
    @ResponseBody
    public ServerResponse handleRegression(@RequestParam(value = "file_name") String fileName,
                                           @RequestParam("access_token") String accessToken,
                                           @PathVariable("classifierId") Integer classifierId,
                                           @RequestParam Map<String, String> param) {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }

        int apiId = 0;
        if (classifierId == 48) {
            API api = apiMapper.selectById(10);
            api.setUsedCount(api.getUsedCount() + 1);
            apiMapper.update(api);
            apiId = api.getId();

        }
        if (classifierId == 44) {
            API api = apiMapper.selectById(11);
            api.setUsedCount(api.getUsedCount() + 1);
            apiMapper.update(api);
            apiId = api.getId();
        }
        if (classifierId == 43) {
            API api = apiMapper.selectById(12);
            api.setUsedCount(api.getUsedCount() + 1);
            apiMapper.update(api);
            apiId = api.getId();
        }

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(apiId);

        CsvDivider.divide(new File(Const.UPLOAD_DATASET, fileName), 0.8);
        File train = new File(Const.UPLOAD_DATASET, CsvDivider.getTrainFileName(fileName, 0.8));
        File test = new File(Const.UPLOAD_DATASET, CsvDivider.getTestFileName(fileName, 0.8));
        ServerResponse serverResponse = componentApiService.execRegressor(train.getAbsolutePath(), test.getAbsolutePath(), param, classifierId);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     * 机器学习回归算法
     */

//    @RequestMapping("/ML/regression/2_file/{classifierId}")
//    @ResponseBody
//    public ServerResponse handleRegression2(@RequestParam(value = "train_file", required = false) MultipartFile trainFile,
//                                            @RequestParam(value = "Base64_train", required = false) String base64Train,
//                                            @RequestParam(value = "test_file", required = false) MultipartFile testFile,
//                                            @RequestParam(value = "train_name", required = false) String trainName,
//                                            @RequestParam(value = "test_name", required = false) String testName,
//                                            @RequestParam(value = "Base64_test", required = false) String base64Test,
//                                            @RequestParam("access_token") String accessToken,
//                                            @PathVariable("classifierId") Integer classifierId,
//                                            @RequestParam Map<String, String> param) throws IOException {
//        ServerResponse response = authService.checkAccessToken(accessToken);
//        if (!response.isSuccess()) {
//            return response;
//        }
//        File train;
//        if (trainName != null) {
//            train = new File(Const.UPLOAD_DATASET, trainName);
//        } else {
//            if (trainFile != null) {
//                ServerResponse fileResponse = fileService.checkFileSize(trainFile);
//                if (!fileResponse.isSuccess()) {
//                    return fileResponse;
//                }
//                train = fileService.addFile(trainFile, Const.FILE_REGRESSION_ROOT, "train");
//
//            } else {
//                train = fileService.addFile(base64Train, Const.FILE_REGRESSION_ROOT, "train");
//            }
//        }
//        File test;
//        if (testName != null) {
//            test = new File(Const.UPLOAD_DATASET, testName);
//        } else {
//            if (testFile != null) {
//                ServerResponse fileResponse = fileService.checkFileSize(testFile);
//                if (!fileResponse.isSuccess()) {
//                    return fileResponse;
//                }
//                test = fileService.addFile(testFile, Const.FILE_REGRESSION_ROOT, "test");
//
//            } else {
//                test = fileService.addFile(base64Test, Const.FILE_REGRESSION_ROOT, "test");
//            }
//        }
//        return componentApiService.execRegressor(train.getAbsolutePath(), test.getAbsolutePath(), param, classifierId);
//    }

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
     *
     * @param accessToken
     * @param params
     * @return
     */
    @PostMapping("/upload/{apiType}/{uploadAlgoId}")
    public ServerResponse uploadApi(@RequestParam(value = "file_name", required = false) String fileName,
                                    @RequestParam("access_token") String accessToken,
                                    @RequestParam Map<String, Object> params,
                                    @PathVariable("apiType") Integer apiType,
                                    @PathVariable("uploadAlgoId") Integer uploadAlgoId) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }


        API api = componentApiService.getAPIByUploadAlgoId1(uploadAlgoId,apiType);

        if (api.getPub().equals(0)) {
            String apikey = accessToken.split("\\.")[0];
            ApiAuth apiAuth = authService.getApiAuthByApiKey(apikey);
            System.out.println(apiAuth.getUserId() + "  " + api.getUserId());
            if (!apiAuth.getUserId().equals(api.getUserId())) {
                return ServerResponse.createByErrorMessage("该api是私有的，您无使用权限");
            }
        }
        if (api.getUsedCount() == null) {
            api.setUsedCount(1);
        } else {
            api.setUsedCount(api.getUsedCount() + 1);
        }
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(api.getId());

        ServerResponse serverResponse = componentApiService.execUploadPreAndFea(Const.UPLOAD_DATASET + fileName, params, apiType, uploadAlgoId);
        if(serverResponse.isSuccess()){
            userRecord.setState(0);
            userRecord.setMsg("upload");
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;

    }

    /**
     * 上传的算法生成的api
     *
     * @param accessToken
     * @param params
     * @return
     */
    @PostMapping("/upload/ML/{apiType}/{uploadAlgoId}")
    public ServerResponse uploadMLApi(@RequestParam(value = "file_name") String fileName,
                                      @RequestParam("access_token") String accessToken,
                                      @RequestParam Map<String, Object> params,
                                      @PathVariable("apiType") Integer apiType,
                                      @PathVariable("uploadAlgoId") Integer uploadAlgoId) throws IOException {

        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }

        API api = componentApiService.getAPIByUploadAlgoId1(uploadAlgoId,apiType);
        if (api.getPub().equals(0)) {
            String apikey = accessToken.split("\\.")[0];
            ApiAuth apiAuth = authService.getApiAuthByApiKey(apikey);
            if (!apiAuth.getUserId().equals(api.getUserId())) {
                return ServerResponse.createByErrorMessage("该api是私有的，您无使用权限");
            }
        }
        if (api.getUsedCount() == null) {
            api.setUsedCount(1);
        } else {
            api.setUsedCount(api.getUsedCount() + 1);
        }
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(api.getId());

        CsvDivider.divide(new File(Const.UPLOAD_DATASET, fileName), 0.8);
        File train = new File(Const.UPLOAD_DATASET, CsvDivider.getTrainFileName(fileName, 0.8));
        File test = new File(Const.UPLOAD_DATASET, CsvDivider.getTestFileName(fileName, 0.8));

        ServerResponse serverResponse = componentApiService.execUploadML(train.getAbsolutePath(), test.getAbsolutePath(), params, api.getUploadAlgoId(), apiType);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
            userRecord.setMsg("upload");
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    @PostMapping("/upload/ML/model/{modelId}/{uploadAlgoId}")
    public ServerResponse uploadMLModelApi(@RequestParam(value = "file_name") String fileName,
                                           @RequestParam("access_token") String accessToken,
                                           @PathVariable("modelId") Integer modelId,
                                           @PathVariable("uploadAlgoId") Integer uploadAlgoId) throws IOException {

        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }

        API api = componentApiService.getAPIByUploadAlgoId(uploadAlgoId,5);
        if (api.getUsedCount() == null) {
            api.setUsedCount(1);
        } else {
            api.setUsedCount(api.getUsedCount() + 1);
        }
        apiMapper.update(api);

        Model model = modelService.getModelById(modelId);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(4);
        userRecord.setRecordTypeId(modelId);

        ServerResponse serverResponse = componentApiService.execUploadModel(fileName, uploadAlgoId, model);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
            userRecord.setMsg("upload");
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    @PostMapping("/upload/zhoucheng/{apiType}/{apiId}")
    public ServerResponse uploadPreFeaApi(@RequestParam(value = "file_name") String fileName,
                                          @RequestParam("access_token") String accessToken,
                                          @RequestParam Map<String, Object> params,
                                          @PathVariable("apiType") Integer apiType,
                                          @PathVariable("apiId") Integer apiId) throws IOException {

        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = componentApiService.getAPIById(apiId);
        if (api.getUsedCount() == null) {
            api.setUsedCount(1);
        } else {
            api.setUsedCount(api.getUsedCount() + 1);
        }
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(2);
        userRecord.setRecordTypeId(apiId);

        ServerResponse serverResponse = componentApiService.execUploadPreAndFea2(fileName, params, apiType, apiId);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     * 获取风机数据
     */

    @PostMapping("/data/fengji")
    public ServerResponse getData(@RequestParam("access_token") String accessToken,
                                  @RequestParam("divice_id") Integer diviceId,
                                  @RequestParam("group_id") String groupIds,
                                  @RequestParam("atrribute") String atrributeName) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = componentApiService.getAPIById(21);
        if (api.getUsedCount() == null) {
            api.setUsedCount(1);
        } else {
            api.setUsedCount(api.getUsedCount() + 1);
        }
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(3);
        userRecord.setRecordTypeId(api.getId());

        ServerResponse serverResponse = componentApiService.execFjData(diviceId, groupIds, atrributeName);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     * 获取轴承数据
     */

    @PostMapping("/data/zhoucheng")
    public ServerResponse getData(@RequestParam("access_token") String accessToken,
                                  @RequestParam("divice_id") Integer diviceId,
                                  @RequestParam("atrribute") String atrributeName) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = componentApiService.getAPIById(59);
        if (api.getUsedCount() == null) {
            api.setUsedCount(1);
        } else {
            api.setUsedCount(api.getUsedCount() + 1);
        }
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(3);
        userRecord.setRecordTypeId(api.getId());

        ServerResponse serverResponse = componentApiService.execZcData(diviceId, atrributeName);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    /**
     *  获取paderborn轴承数据
     */
    @PostMapping("/data/paderborn")
    public ServerResponse getData(@RequestParam("device_id")String deviceId,
                                  @RequestParam("access_token")String accessToken,
                                  @RequestParam("attribute")String attributeName) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        API api = componentApiService.getAPIById(355);
        if (api.getUsedCount() == null) {
            api.setUsedCount(1);
        } else {
            api.setUsedCount(api.getUsedCount() + 1);
        }
        apiMapper.update(api);

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(3);
        userRecord.setRecordTypeId(api.getId());


        ServerResponse serverResponse = componentApiService.execPzcData(deviceId, attributeName);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;

    }
    /**
     * 获取上传的原始数据
     */

    @PostMapping("/upload/data/{fileName}")
    @ResponseBody
    public ServerResponse getData2(@RequestParam("access_token") String accessToken,
                                   @RequestParam("attribute") String attributeName,
                                   @PathVariable("fileName") String fileName) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }

        String localUrl = Const.UPLOAD_DATASET + fileName + ".csv";
        System.out.println(localUrl);
        Dataset dataset = datasetMapper.selectByLocalUrl(localUrl);
        if(dataset==null){
            return ServerResponse.createByErrorMessage("数据集不存在");
        }
        API api = apiMapper.getAPIByDataId(dataset.getId());

        UserRecord userRecord = new UserRecord();
        userRecord.setUserId((int) response.getData());
        userRecord.setRecordTime(new Date());
        userRecord.setRecordType(3);
        userRecord.setRecordTypeId(api.getId());

        ServerResponse serverResponse = componentApiService.getData(dataset.getId(),attributeName);

        if(serverResponse.isSuccess()){
            userRecord.setState(0);
            userRecord.setMsg("upload");
        }else{
            userRecord.setState(1);
            userRecord.setMsg(serverResponse.getMsg());
        }
        userRecordMapper.insert(userRecord);

        return serverResponse;
    }

    @PostMapping("/ML/predict/{classifierId}")
    public ServerResponse predict(@RequestParam("access_token") String accessToken,
                                  @RequestParam(value = "file_name", required = false) String fileName,
                                  @RequestParam Map<String, Object> params,
                                  @PathVariable("classifierId") Integer classifierId) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }

        return componentApiService.execPredict(fileName, params, classifierId);
    }

    @PostMapping("/feature/zhoucheng")
    public ServerResponse predict(@RequestParam("access_token") String accessToken,
                                  @RequestParam(value = "file_name", required = false) String fileName) throws IOException {
        ServerResponse response = authService.checkAccessToken(accessToken);
        if (!response.isSuccess()) {
            return response;
        }
        String opath = Const.FILE_OPATH_FEA_ZHOUCHENG + "out_" + fileName + ".csv";
        File opathFile = new File(opath);
        if (opathFile.exists()) {
            Map<String, Object> map = new HashMap<>();
            map.put("file_name", opathFile.getName());
            return ServerResponse.createBySuccess(map);
        }

        StringBuilder sb = new StringBuilder("python ");
        sb.append(Const.FILE_FEA_ZHOUCHENG).append(" ");
        sb.append("path=").append(Const.FILE_OPATH_FEA_ZHOUCHENG + fileName).append(" ");
        sb.append("opath=").append(opath);

        System.out.println(sb.toString());
        PythonUtils.execPy(sb.toString());
        if (!opathFile.exists() || opathFile.length() <= 0) {
            return ServerResponse.createByErrorMessage("处理失败，请检验数据格式是否正确");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("file_name", opathFile.getName());
        return ServerResponse.createBySuccess(map);
    }
}
