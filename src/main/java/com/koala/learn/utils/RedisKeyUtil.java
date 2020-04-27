package com.koala.learn.utils;

import com.google.gson.Gson;
import com.koala.learn.entity.Classifier;

import java.util.Map;

/**
 * Created by nowcoder on 2016/7/30.
 */
public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";
    // 获取粉丝
    private static String BIZ_FOLLOWER = "FOLLOWER";
    // 关注对象
    private static String BIZ_FOLLOWEE = "FOLLOWEE";
    private static String BIZ_TIMELINE = "TIMELINE";

    private static String INIT_SMOTE="INIT_SMOTE";
    private static String GET_SMOTE="GET_SMOTE";
    private static String GET_ISOLATION="GET_ISOLATION";
    private static String GET_NORMALIZATION="GET_NORMALIZATION";

    private static String WX_COMPONENT_ALGORITHM_CACHE ="WX_COMPONENT_ALGORITHM_CACHE";
    private static String WX_COMPONENT_ALGO_PARAMS_CACHE ="WX_COMPONENT_ALGO_PARAMS_CACHE";
    private static String WX_LAB_RECORD="WX_LAB_RECORD";
    private static String BIZ_ATTRIBUTE = "ATTRIBUTE";

    private static String BIZ_ATTRIBUTEVIEW = "ATTRIBUTEVIEW";


    private static String BIZ_ATTRIBUTELIST = "ATTRIBUTELIST";

    private static String BIZ_LABVIEW = "LABVIEW";
    private static String BIZ_FEATURE = "FEATURE";
    private static String BIZ_PREHANDLE = "PRE_HANDLE";
    private static String BIZ_CLASSIFIER = "CLASSIFIER";
    private static String BIZ_FEATURE_DES = "FEATURE_DES";
    private static String BIZ_PRE_DES = "PRE_DES";
    private static String BIZ_CLASSIFIER_DES = "CLASSIFIER_DES";
    private static String BIZ_DIVIDER = "DIVIDER";
    private static String BIZ_DIVIDER_DES = "DIVIDER_DES";
    private static String BIZ_FILE = "FILE";
    private static String GET_FFT="GET_FFT";

    private static String BIZ_PRE_INSTANCE = "PRE_INSTANCE";
    private static String BIZ_FEATURE_INSTANCE = "FEATURE_INSTANCE";
    private static String BIZ_FILE_INSTANCE = "FILE_INSTANCE";
    private static String BIZ_FILE_FEATURE = "FILE_FEATURE";
    private static String BIZ_FILE_PRE = "FILE_PRE";

    private static String BIZ_DIVIDER_INSTANCE = "DIVIDER_INSTANCE";

    private static String BIZ_RES_INSTANCE = "RES_INSTANCE";

    private static String BIZ_RES_LAB = "RES_LAB";

    private static String BIZ_DIVIDER_OUT = "DIVIDER_OUT";

    private static String BIZ_CACHE_KEY = "CACHE";

    private static String BIZ_PCA_KEY = "PCA";

    private static String DATA_ZHOUCHENG_KEY="DATA_ZHOUCHENG";

    private static Gson gson = new Gson();

    public static String getIsolationKey(){
        return GET_ISOLATION+SPLIT+"init";
    }
    public static String getNormalizationKey(){
        return GET_NORMALIZATION+SPLIT+"init";
    }
    public static String getNormalizationKey(String str){
        return GET_NORMALIZATION+SPLIT+str;
    }
    public static String getfftKey(String attr){
        return GET_FFT+SPLIT+attr;
    }
    public static String getIsolationKey(Double contamination){
        return GET_ISOLATION+SPLIT+contamination;
    }
    public static String getInitSmote(){
        return INIT_SMOTE;
    }
    public static String getSmoteKey(Integer kNeighbors,Integer ratio){
        return GET_SMOTE+SPLIT+kNeighbors+SPLIT+ratio;
    }
    public static String getWxComponentAlgorithmCache(Map<String,String> param, Integer classifierId){
        StringBuilder res=new StringBuilder();
        res.append(WX_COMPONENT_ALGORITHM_CACHE).append(SPLIT).append(classifierId).append(SPLIT);
        for(String key:param.keySet()){
            res.append(key).append(param.get(key));
        }
        return res.toString();
    }
    public static String getWxComponentAlgorithmParamsCache(Map<String,String> param, Integer classifierId){
        StringBuilder res=new StringBuilder();
        res.append(WX_COMPONENT_ALGO_PARAMS_CACHE).append(SPLIT).append(classifierId).append(SPLIT);
        for(String key:param.keySet()){
            res.append(key).append(param.get(key));
        }
        return res.toString();
    }

    public static String getWxLabRecord(String openId){
        return WX_LAB_RECORD+SPLIT+openId;
    }

    public static String getPCAKey(int dimension){
        return BIZ_PCA_KEY+SPLIT+ String.valueOf(dimension);

    }
    public static String getZhouchengDataKey(Integer diviceId, String atrributeName){
        return DATA_ZHOUCHENG_KEY+SPLIT+ String.valueOf(diviceId)+SPLIT+atrributeName;

    }
    public static String getLikeKey(int entityType, int entityId) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getDisLikeKey(int entityType, int entityId) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }

    // 某个实体的粉丝key
    public static String getFollowerKey(int entityType, int entityId) {
        return BIZ_FOLLOWER + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    // 每个用户对某类实体的关注key
    public static String getFolloweeKey(int userId, int entityType) {
        return BIZ_FOLLOWEE + SPLIT + String.valueOf(userId) + SPLIT + String.valueOf(entityType);
    }

    public static String getTimelineKey(int userId) {
        return BIZ_TIMELINE + SPLIT + String.valueOf(userId);
    }

    public static String getAttributeKey(String attribute,String labId){
        return BIZ_ATTRIBUTE + SPLIT + labId+SPLIT+attribute;
    }

    public static String getAttributeKey(String attribute,Integer viewType,Integer labId){
        if (viewType==0 || viewType == 3 || viewType == 6){
            return BIZ_ATTRIBUTEVIEW + SPLIT + labId+SPLIT+viewType;
        }
        return BIZ_ATTRIBUTEVIEW + SPLIT + labId+SPLIT+attribute+SPLIT+viewType;
    }

    public static String getLabViewKey(Integer labId){
        return BIZ_LABVIEW+SPLIT+labId;
    }

    public static String getFeatureKey(Integer labId){
        return BIZ_FEATURE+SPLIT+labId;
    }
    public static String getPreKey(Integer labId){
        return BIZ_PREHANDLE+SPLIT+labId;
    }

    public static String getClassifierKey(Integer labId){
        return BIZ_CLASSIFIER+SPLIT+labId;
    }

    public static String getFeatureDesKey(Integer labId){
        return BIZ_FEATURE_DES+SPLIT+labId;
    }
    public static String getPreDesKey(Integer labId){
        return BIZ_PRE_DES+SPLIT+labId;
    }

    public static String getClassifierDesKey(Integer labId){
        return BIZ_CLASSIFIER_DES+SPLIT+labId;
    }

    public static String getDividerKey(Integer labId){
        return BIZ_DIVIDER+SPLIT+labId;
    }

    public static String getDividerDesKey(Integer labId){
        return BIZ_DIVIDER_DES+SPLIT+labId;
    }

    public static String getAttributeListKey(Integer labId){
        return BIZ_ATTRIBUTELIST+SPLIT+labId;
    }


    public static String getFeatureInstanceKey(Integer labId,Integer instanceId){
        return BIZ_FEATURE_INSTANCE+SPLIT+labId+SPLIT+instanceId;
    }
    public static String getPreInstanceKey(Integer labId,Integer instanceId){
        return BIZ_PRE_INSTANCE+SPLIT+labId+SPLIT+instanceId;
    }

    private final static String BIZ_CLASSIFIER_INSTANCE = "CLASSIFIER_INSTANCE";
    public static String getClassifierInstanceKey(Integer labId,Integer instanceId){
        return BIZ_CLASSIFIER_INSTANCE+SPLIT+labId+SPLIT+instanceId;
    }

    public static String getFileKey(Integer labId){
        return BIZ_FILE+SPLIT+labId;
    }

    public static String getFilePreKey(Integer labId){
        return BIZ_FILE_PRE+SPLIT+labId+SPLIT;
    }

    public static String getFileFeatureKey(Integer labId,Integer featureId){
        return BIZ_FILE_FEATURE+SPLIT+labId+SPLIT+featureId;
    }

    public static String getFileInstanceKey(Integer labId,Integer instance){
        return BIZ_FILE_INSTANCE+SPLIT+labId+SPLIT+instance;
    }

    public static String getResLabKey(Integer labId,Classifier classifier){
        return BIZ_RES_LAB+SPLIT+labId+SPLIT+classifier.getName()+gson.toJson(classifier).hashCode();
    }

    public static String getResInstanceKey(Integer labId, Integer instance){

        return BIZ_RES_INSTANCE+SPLIT+labId+SPLIT+instance;
    }

    public static String getResInstanceKey(Integer labId, Integer instance, Classifier classifier){
        if (classifier==null){
            return BIZ_RES_INSTANCE+SPLIT+labId+SPLIT+instance;
        }else {
            return BIZ_RES_INSTANCE+SPLIT+labId+SPLIT+instance+SPLIT+classifier.getName()+gson.toJson(classifier).hashCode();
        }

    }

    public static String getDividerInstanceKey(Integer labId,Integer instance){
        return BIZ_DIVIDER_INSTANCE+SPLIT+labId+SPLIT+instance;
    }

    public static String getDividerOutKey(Integer labId,Integer instance){
        return BIZ_DIVIDER_OUT+SPLIT+labId+SPLIT+instance;
    }

    public static String getCacheKey(Integer labId,String file,String classifier){
        return BIZ_CACHE_KEY+SPLIT+labId+SPLIT+file+SPLIT+classifier;
    }

    public static String getPyKey(Integer labId){
        return "PY"+SPLIT+ labId;
    }

    public static String getUpClassKey(Integer classId){
        return "upClass"+SPLIT+classId;
    }
}
