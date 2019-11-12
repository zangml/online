package com.koala.learn.utils.feature;

import com.koala.learn.Const;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.WekaUtils;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.File;
import java.io.IOException;



public class WavedecFeature implements IFeature {

    private String waveLayer;
    private String windowLength;

    @Override
    public void setOptions(String[] options) {
        waveLayer = options[3];
        windowLength=options[1];
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
            if (file.getAbsolutePath().endsWith("arff"))
            {
                out = new File(out.getAbsolutePath().replace("arff","csv"));
            }
            String wavedec = "python "+ Const.WAVE_FEATURE+ " wave_layer="+waveLayer+ " len_piece="+windowLength
                    +" path="+file.getAbsolutePath()+" opath="+out;
            System.out.println(wavedec);
            PythonUtils.execPy(wavedec);
            out=WekaUtils.csv2arff(out);
            return out;
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
        }

    public static void main(String[] args) throws Exception {
        String path = "/Users/zangmenglei/mylib/diabetes.arff";
        String opath = "/Users/zangmenglei/mylib/diabeteswav-wmass.csv";
        String[] options = {"-w", "preg"};
        WavedecFeature wavedecFeature = new WavedecFeature();
        wavedecFeature.setOptions(options);
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File(path));
        wavedecFeature.filter(null,new File(path),new File(opath));
    }
}
