package com.koala.learn.utils.feature;

import com.koala.learn.Const;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.WekaUtils;
import weka.core.Instances;

import java.io.File;
import java.io.IOException;



public class TimeFeature implements IFeature {

    private String windowLength;

    @Override
    public void setOptions(String[] options) {
        windowLength = options[1];
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
            String timeFeatureDec = "python "+ Const.TIME_FEATURE+ " len_piece="+windowLength
                    +" path="+file.getAbsolutePath()+" opath="+out;
            System.out.println(timeFeatureDec);
            PythonUtils.execPy(timeFeatureDec);
            out=WekaUtils.csv2arff(out);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
