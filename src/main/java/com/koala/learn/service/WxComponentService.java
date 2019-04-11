package com.koala.learn.service;


import com.koala.learn.Const;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.WekaUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class WxComponentService {

    public File handleOcSvm(double nu,File input) throws IOException {
        input = WekaUtils.arff2csv(input);

        File out=new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv", "") +"nu" +nu + ".csv");
        if(out.exists()){
            System.out.println("从已有文件中获取");
            out=WekaUtils.csv2arff(out);
            return out;
        }
        String ocsvmdec = "python "+ Const.OCSVM_FOR_WX+ " nu="+nu
                +" path="+input.getAbsolutePath()+" opath="+out;
        System.out.println(ocsvmdec);
        PythonUtils.execPy(ocsvmdec);
        out=WekaUtils.csv2arff(out);
        return out;
    }

    public File handleIsolation(double contamination,File input) throws IOException {
        input = WekaUtils.arff2csv(input);
        File out=new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv", "") +"con" +contamination+ ".csv");
        if(out.exists()){
            out=WekaUtils.csv2arff(out);
            return out;
        }
        String isoLationmdec = "python "+ Const.ISOLATIONFOREST_FOR_WX+ " contamination="+contamination
                +" path="+input.getAbsolutePath()+" opath="+out;
        System.out.println(isoLationmdec);
        PythonUtils.execPy(isoLationmdec);
        out=WekaUtils.csv2arff(out);
        return out;
    }

    public File handleFFT(String attribute,File input) throws IOException {
        input = WekaUtils.arff2csv(input);
        File out = new File(Const.ROOT_FOR_DATA_WX,
                input.getName().replace(".csv", "") +
                        "attribute_" + attribute + ".csv");
        if (out.exists()) {
            out = WekaUtils.csv2arff(out);
            return out;
        }
        String fftDesc = "python " + Const.FFT_FOR_WX + " attribute=" + attribute
                + " path=" + input.getAbsolutePath() + " opath=" + out;
        System.out.println(fftDesc);
        PythonUtils.execPy(fftDesc);
        out = WekaUtils.csv2arff(out);
        return out;
    }
}
