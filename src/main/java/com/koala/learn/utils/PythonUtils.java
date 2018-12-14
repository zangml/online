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
        String out = execPy("python /usr/local/sk/GBDT.py n_estimators=100 learning_rate=1 subsample=1.0 max_depth=3 min_samples_split=2 min_samples_leaf=1 train=/Users/zangmenglei/PHM/137/random0.8diabeteswindow-L2-S1train.csv test=/Users/zangmenglei/PHM/137/random0.8diabeteswindow-L2-S1test.csv");
        System.out.println(out);
    }
}
     //PHM领域的算法 封装成模型 结合具体问题进行尝试；


