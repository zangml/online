package com.koala.learn.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by koala on 2018/3/6.
 */
public class PythonUtils {
    public static String execPy(String py){
        StringBuilder sb = new StringBuilder();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(py);
            process.waitFor();
            BufferedReader stdOut=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s;
            while((s=stdOut.readLine())!=null){
                sb.append(s);
            }
        } catch (IOException e) {
            sb.append(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public static void main(String[] args)  {
        //String out = execPy("python /Users/zangmenglei/Python/LR.py penalty=l2  tol=0.0001 random_state=None fit_intercept=True solver=sag multi_class=ovr class_weight=None C=1.0 train=/Users/zangmenglei/train.csv test=/Users/zangmenglei/test.csv");
        //System.out.println(out);
        String out = execPy("python /usr/local/sk/LocalOutlierFactor.py contamination=0.001 path=/Users/zangmenglei/PHM/341/diabetes.csv opath=/Users/zangmenglei/PHM/341/diabeteslof-lof0.001.csv");
        System.out.println(out);
    }
}
     //PHM领域的算法 封装成模型 结合具体问题进行尝试；


