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
        String isoLationmdec = "python "+ Const.ISOLATIONFOREST_FOR_WX+ " contamination="+contamination
                +" path="+input.getAbsolutePath()+" opath="+out;
        System.out.println(isoLationmdec);
        PythonUtils.execPy(isoLationmdec);
        out=WekaUtils.csv2arff(out);
        return out;
    }
}
