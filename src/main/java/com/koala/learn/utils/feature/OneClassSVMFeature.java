package com.koala.learn.utils.feature;

import com.koala.learn.Const;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.WekaUtils;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;

public class OneClassSVMFeature implements IFeature {

    private String nu;
    @Override
    public void setOptions(String[] options) {
        nu=options[1];

    }

    @Override
    public File filter(Instances input, File file, File out) throws IOException {
        if (out.exists()) {
            return out;
        }
        if(file.getAbsolutePath().endsWith("arff")) {
            file = WekaUtils.arff2csv(file);
        }
        try{
            String ocsvmdec = "python "+ Const.ONECLASSSVM_FEATURE+ " nu="+nu
                    +" path="+file.getAbsolutePath()+" opath="+out;
            System.out.println(ocsvmdec);
            PythonUtils.execPy(ocsvmdec);
            out=WekaUtils.csv2arff(out);
            return out;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
