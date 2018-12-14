package com.koala.learn.utils.feature;

import com.koala.learn.utils.WekaUtils;
import com.koala.learn.utils.WindowUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import weka.core.Instances;

/**
 * Created by koala on 2018/1/12.
 */
public class WindowFeature implements IFeature {
    private int length;
    private int step;

    @Override
    public void setOptions(String[] options) {
        for (int i=0;i<options.length;i++){
            if (options[i].equals("-L")){
                length = new Integer(options[i+1]);
            }else if (options[i].equals("-S")){
                step = new Integer(options[i+1]);
            }
        }
    }

    @Override
    public File filter(Instances input, File file, File out) {
        if (out.exists()) return out;
        try {
            WindowUtils.window(input,length,step,out);
            return out;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
