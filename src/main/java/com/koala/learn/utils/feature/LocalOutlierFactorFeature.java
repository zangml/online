package com.koala.learn.utils.feature;

import com.koala.learn.Const;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.WekaUtils;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;

public class LocalOutlierFactorFeature implements IFeature {
    private String contamination;
    @Override
    public void setOptions(String[] options) {
        contamination=options[1];

    }

    @Override
    public File filter(Instances input, File file, File out) throws IOException {
        if(file.getAbsolutePath().endsWith("arff")) {
            file = WekaUtils.arff2csv(file);
        }
        try{
            if (file.getAbsolutePath().endsWith("arff"))
            {
                out = new File(out.getAbsolutePath().replace("arff","csv"));
            }
            String ifdec = "python "+ Const.LOCALOUTLIERFACTOR_FEATURE+ " contamination="+contamination
                    +" path="+file.getAbsolutePath()+" opath="+out;
            System.out.println(ifdec);
            PythonUtils.execPy(ifdec);
            out=WekaUtils.csv2arff(out);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
