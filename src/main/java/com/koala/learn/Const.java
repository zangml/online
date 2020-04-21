package com.koala.learn;

/**
 * Created by koala on 2017/11/8.
 */
public class Const {

//    public static String ROOT = "/data/learn/";
    public static final String ROOT = "/Users/zangmenglei/PHM/";//根目录

    public static final String UPLOAD_DATASET = "/usr/local/data/upload/";//根目录

    public static final String ROOT_DATASET = "/usr/local/data/";//根目录

    public static final String DOWNLOAD_FILE_PREFIX = "http://file.phmlearn.com/";
    public static final String UPLOAD_FILE_ROOT = "/ftpfile/fileForPHM/";

    public static final String WINDOW = "currentWindow";

    public static final int CLASSIFIER = 1;
    public static final int REGRESSION = 2;
    public static final int CLASSIFIER_PREDICT = 3;
    public static final int REGRESSION_PREDICT = 4;
    public static final int CLASSIFIER_PREDICT_RESULT = 5;
    public static final int REGRESSION_PREDICT_RESULT = 6;

    /*
         <option value="1">数据预处理</option>
         <option value="2">特征提取</option>
         <option value="3">故障诊断（分类）</option>
         <option value="4">寿命预测（回归）</option>
     */
    public static final int UPLOAD_ALGO_TYPE_PRE=1;
    public static final int UPLOAD_ALGO_TYPE_FEA=2;
    public static final int UPLOAD_ALGO_TYPE_CLA=3;
    public static final int UPLOAD_ALGO_TYPE_REG=4;
    public static final int UPLOAD_ALGO_TYPE_MODEL_CLA=6;
    public static final int UPLOAD_ALGO_TYPE_MODEL_REG=7;
    public static final int UPLOAD_ALGO_TYPE_MODEL_PREDICT_CLA=8;
    public static final int UPLOAD_ALGO_TYPE_MODEL_PREDICT_REG=9;
    public static final int UPLOAD_ALGO_TYPE_MODEL_PREDICT_RES_CLA=10;
    public static final int UPLOAD_ALGO_TYPE_MODEL_PREDICT_RES_REG=11;
    public static final String UPDATE_CLASS_ROOT_PRE = "/usr/local/sk/upload/pre/"; //上传算法所在目录

    public static final String UPDATE_CLASS_ROOT_FEA = "/usr/local/sk/upload/fea/";

    public static final String UPDATE_CLASS_ROOT_CLA = "/usr/local/sk/upload/cla/";

    public static final String UPDATE_CLASS_ROOT_REG = "/usr/local/sk/upload/reg/";


    public static final String UPDATE_CLASS_ROOT_MODEL = "/usr/local/sk/upload/model/";

    public static final String DEFAULT_AVATAR = "http://image.phmlearn.com/02a885be-28de-414c-9f61-47845fc1c35e.jpeg";


    public static final String UPLOAD_CLASS_TEST_ROOT_PRE = "/Users/zangmenglei/data/upload/pre/"; //上传算法测试文件所在目录

    public static final String UPLOAD_CLASS_TEST_ROOT_FEA = "/Users/zangmenglei/data/upload/fea/";

    public static final String UPLOAD_CLASS_TEST_ROOT_CLA = "/Users/zangmenglei/data/upload/cla/";

    public static final String UPLOAD_CLASS_TEST_ROOT_REG = "/Users/zangmenglei/data/upload/reg/";

    public static final String UPDATE_CLASS_ROOT_SCORE = "/usr/local/data/upload/score/";

    public static final String UPLOAD_CLASS_TEST_ROOT_PRE_OPATH = "/Users/zangmenglei/data/upload/pre/opath/"; //上传算法输出文件所在目录

    public static final String UPLOAD_CLASS_TEST_ROOT_FEA_OPATH = "/Users/zangmenglei/data/upload/fea/opath/";


    public static final String IMAGE = "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3972224591,2666750413&fm=173&s=84DB4D325D8A604F4E6125DA0000C0B2&w=600&h=475&img.JPEG";

    public static final String IMAGE_FINISH = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522231621827&di=4f6231d529616d30d4ab7d6efb4a2c94&imgtype=0&src=http%3A%2F%2Fi1.bbswater.fd.zol-img.com.cn%2Ft_s480x480%2Fg5%2FM00%2F03%2F0C%2FChMkJ1lKEKaIAukFAAMbOVmHWTAAAdKmQPU0cEAAxtR277.png";

    public static final String RELATIVE_CMD = "/usr/local/sk/relative.py";  //生成分类模型特征重要性分析的python文件

    public static final String REG_RELATIVE_CMD ="/usr/local/sk/regRelative.py"; //生成回归模型特征重要性分析的python文件

    public static final String WAVEDEC_FEATURE ="/usr/local/sk/wavedecFeature.py"; //封装的小波变换python文件

    public static final String ONECLASSSVM_FEATURE ="/usr/local/sk/OneClassSVM.py"; //封装的OneClassSVMpython文件

    public static final String ISOLATIONFOREST_FEATURE ="/usr/local/sk/IsolationForest.py"; //封装的Isolationpython文件

    public static final String LOCALOUTLIERFACTOR_FEATURE ="/usr/local/sk/LocalOutlierFactor.py"; //封装的LocalOutlierFactorn文件

    public static final String DATA_FOR_SMOTE="/usr/local/data/wx/dataForSmote.arff"; //微信调用Smote组件时用于展示的数据集

    public static final String DATA_FOR_OCSVM="/usr/local/data/wx/dataForOCSVM.arff";//微信调用OneClassSVM组件时用于展示的数据集

    public static final String OCSVM_FOR_WX="/usr/local/sk/OneClassSVMForWx.py";//封装的微信调用OneClassSVM算法

    public static final String ROOT_FOR_DATA_WX="/usr/local/data/wx/result/";//微信调用组件算法产生的输出文件

    public static final String DATA_FOR_ISOLATIONFOREST="/usr/local/data/wx/dataForIsoLationForest.arff";//微信调用Isolation组件时用于展示的数据集
    public static final String DATA_FOR_INIT_ISOLATIONFOREST="/usr/local/data/wx/dataForIsoLationForest_init.arff";//微信调用Isolation组件时用于展示的数据集

    public static final String ISOLATIONFOREST_FOR_WX="/usr/local/sk/IsoLationForestForWx.py";//封装的微信调用Isolation算法

    public static final String CLEAR_ISOLATIONFOREST_FOR_WX="/usr/local/sk/IsoLationForestForWxClear.py";

    public static final String DATA_FOR_FFT_WX="/usr/local/data/wx/dataForFFT.arff";//微信调用fft组件时用于展示的数据集

    public static final String FFT_FOR_WX="/usr/local/sk/fftForWx.py";//封装的微信调用fft算法


    public static final String TRAIN_REGRESSION_FOR_DL="/usr/local/data/wx/train_regression.csv"; //深度学习算法组件训练数据

    public static final String TEST_REGRESSION_FOR_DL="/usr/local/data/wx/test_regression.csv";//深度学习算法组件测试数据

    public static final String TRAIN_CLASSIFIER_FOR_WX="/usr/local/data/wx/train_classifier.csv";//微信调用的分类算法组件训练集

    public static final String TEST_CLASSIFIER_FOR_WX="/usr/local/data/wx/test_classifier.csv";//微信调用的分类算法组件测试集

    public static final String TRAIN_REGRESSION_FOR_WX="/usr/local/data/wx/train_regression.csv";//微信调用的回归算法组件训练集

    public static final String TEST_REGRESSION_FOR_WX="/usr/local/data/wx/test_regression.csv";//微信调用的回归算法组件测试集

    public static final String NORMALIZATION_FEATURE ="/usr/local/sk/Normalization.py"; //封装的OneClassSVMpython文件

    public static final String DATA_FOR_NORMALIZATION ="/usr/local/data/wx/dataForNormalization.arff"; //用于微信调用的归一化数据

    public static final String NORMALIZATION_FOR_WX ="/usr/local/sk/Normalization.py"; //封装的归一化算法文件

    public static final String TIME_FEATURE ="/usr/local/sk/timeFeature.py"; //封装的时域特征提取算法

    public static final String TIME_FEATURE_FOR_WX ="/usr/local/sk/timeFeatureForWxWithoutLable.py"; //封装的微信调用的时域特征提取算法

    public static final String DATA_FOR_TIMEFEATURE ="/usr/local/data/wx/dataForTimeFeature.arff"; //封装的用于展示时域特征提取的数据

    public static final String DATA_FOR_TIMEFEATURE_DOWNLOAD ="/usr/local/data/wx/download/dataForTimeFeature.xls"; //供下载

    public static final String DATA_FOR_NORMALIZATION_DOWNLOAD ="/usr/local/data/wx/download/dataForNormalization.xls"; //供下载

    public static final String DATA_FOR_NORMALIZATION_HANDLED_DOWNLOAD ="/usr/local/data/wx/download/dataForNormalizationNormal(0,1).xls"; //供下载

    public static final String DATA_FOR_WAVEST_WX ="/usr/local/data/wx/dataForWavest.arff"; //用于微信调用小波提取的原始文件

    public static final String WAVE_FEATURE="/usr/local/sk/waveFeature.py";//封装的所有特征提取的python文件

    public static final String ALL_FEATURE="/usr/local/sk/feature_all.py";//封装的小波变换python文件

    public static final String DATA_FOR_PCA_WX="/usr/local/data/wx/dataForPCA.csv";//微信调用pca组件时用于展示的数据集

    public static final String PCA_WX="/usr/local/sk/pca_wx.py";//微信调用pca组件

    public static final String MQ_QUEUE_KEY="phmQueueKey";
    public static final String MQ_QUEUE="phmQueue";
    public static final String MQ_ALGO_QUEUE="algoQueue";
    public static final String MQ_ALGO_QUEUE_KEY="algoQueueKey";
    public static final String MQ_EXCHANGE="phmExchange";

    public static final Integer SMOTE_ID=3;
    public static final Integer ONECLASSSVM_ID=7;
    public static final Integer ISOLATION_FOREST_ID=8;
    public static final Integer NORMALIZATION_ID=10;
    public static final Integer FFT_ID=4;
    public static final Integer TIME_FEATURE_ID=6;
    public static final Integer WAV_FEATURE_ID=5;

    public static final String FILE_ROOT="/usr/local/data/api/file/";

    public static final String FILE_PRE_ROOT="/usr/local/data/api/file/pre/";

    public static final String FILE_FEATURE_ROOT="/usr/local/data/api/file/fea/";

    public static final String FILE_CLASSIFY_ROOT="/usr/local/data/api/file/cla/";

    public static final String FILE_REGRESSION_ROOT="/usr/local/data/api/file/reg/";

    public static final String FILE_OPATH_ROOT="/usr/local/data/upload/";

    public static final String DATA_ZHOUCHENG="/usr/local/data/zhoucheng/data/";

    public static final String DATA_ZHOUCHENG_OUT="/usr/local/data/zhoucheng/data/out/";

    public static final String FILE_OPATH_FEA_ZHOUCHENG="/usr/local/data/zhoucheng/out/fea/";

    public static final String FILE_OPATH_PRE_ZHOUCHENG="/usr/local/data/zhoucheng/out/pre/";



    public static final String FILE_ISOLATIONFOREST ="/usr/local/sk/IsolationForest.py";

    public static final String FILE_NORMALIZATION ="/usr/local/sk/Normalization.py";

    public static final String FILE_SMOTE ="/usr/local/sk/smote_py.py";

    public static final String FILE_TIMEFEATURE ="/usr/local/sk/timeFeature.py";

    public static final String FILE_FREQFEATURE ="/usr/local/sk/freqFeature.py";

    public static final String FILE_WAVFEATURE ="/usr/local/sk/waveFeature_api.py";

    public static final String FILE_FEA_ZHOUCHENG ="/usr/local/sk/feature_zhoucheng.py";

    public static final String FILE_SCORE_CWRU ="/usr/local/sk/score_cwru.py";//CWRU 打分文件

    public static final String FILE_SCORE_CWRU_2 ="/usr/local/sk/score_cwru_2.py";//CWRU_2 打分文件

    public static final String NAME_LAB_CWRU ="CWRU数据故障预测实验（一）";

    public static final String NAME_LAB_CWRU_2 ="CWRU数据故障预测实验（二）";


}
