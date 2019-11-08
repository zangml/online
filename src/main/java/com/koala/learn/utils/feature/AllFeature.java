package com.koala.learn.utils.feature;

import com.koala.learn.Const;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.WekaUtils;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;

public class AllFeature implements IFeature {
    private String windowLength; //窗口长度
    private String avg; //均值
    private String std; //标准差
    private String var; //方差
    private String skew; // 偏度值
    private String kur; //峰度值
    private String ptp; // 峰峰值
    private String freq;//采样频率
    private String minFreq; //要提取的频带能量下限
    private String maxFreq; //要提取的频带能量上限
    private String arNum; //ar系数
    private String mfcc; //mfcc 系数
    private String waveLayer; //小波分解的阶次

    @Override
    public void setOptions(String[] options) {

        windowLength=options[1];
        avg=options[3];
        std=options[5];
        var=options[7];
        skew=options[9];
        kur=options[11];
        ptp=options[13];
        freq=options[15];
        minFreq=options[17];
        maxFreq=options[19];
        arNum=options[21];
        mfcc=options[23];
        waveLayer=options[25];
    }

    @Override
    public File filter(Instances input, File file, File out) throws IOException {
        if (out.exists()) {
            System.out.println("从已有文件中获取timeFeature");
            return out;
        }

        if (file.getAbsolutePath().endsWith("arff")) {
            file = WekaUtils.arff2csv(file);
        }
        try {
            String allFeatureDec = "python3 " + Const.ALL_FEATURE
                    + " len_piece=" + windowLength+ " freq=" + freq+ " wave_layer=" + waveLayer
                    + " avg=" + avg+ " std=" + std+ " var=" + var
                    + " skew=" + skew+ " kur=" + kur+ " ptp=" + ptp
                    + " min_freq=" + minFreq+ " max_freq=" + maxFreq+ " ar_num=" + arNum
                    + " mfcc=" + mfcc
                    + " path=" + file.getAbsolutePath() + " opath=" + out;
            System.out.println(allFeatureDec);
            PythonUtils.execPy(allFeatureDec);
            out = WekaUtils.csv2arff(out);
            return out;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
