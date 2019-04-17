package com.koala.learn;

/**
 * Created by koala on 2017/11/8.
 */
public class Const {

//    public static String ROOT = "/data/learn/";
    public static final String ROOT = "/Users/zangmenglei/PHM/";//根目录

    public static final String WINDOW = "currentWindow";

    public static final String UPDATE_CLASS_ROOT = "/usr/local/sk/"; //上传算法所在目录

    public static final String UPDATE_CLASS_TEST_ROOT = "/Users/zangmenglei/"; //上传算法测试文件所在目录

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

    public static final String ISOLATIONFOREST_FOR_WX="/usr/local/sk/IsoLationForestForWx.py";//封装的微信调用Isolation算法

    public static final String DATA_FOR_FFT_WX="/usr/local/data/wx/dataForFFT.arff";//微信调用fft组件时用于展示的数据集

    public static final String FFT_FOR_WX="/usr/local/sk/fftForWx.py";//封装的微信调用fft算法

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

}
