package com.koala.learn.utils.treat;

import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.entity.EchartOptions3D;
import com.koala.learn.entity.EchatsOptions;
import com.koala.learn.utils.Complex;
import com.koala.learn.utils.PythonUtils;
import com.koala.learn.utils.WekaUtils;
import com.koala.learn.vo.PointVo;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

import com.koala.learn.vo.RelativeVo;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.PrincipalComponents;

/**
 * Created by koala on 2018/1/2.
 */
public class ViewUtils {

    public static final int VIEW_PCA = 0;
    public static final int VIEW_ATTRI = 1;
    public static final int VIEW_MULATTRI = 2;
    public static final int VIEW_RELATIVE = 3;
    public static final int VIEW_REG_ATTRI=4;
    public static final int VIEW_REG_RELATIVE =5;
    public static final int VIEW_PCA_3 =6;
    public static final int VIEW_REG_PCA =7;
    public static final int VIEW_FFT=8;


    private static final int INTERNAL = 80;


    public static Map<Integer,String> getTypeMap(){
        Map<Integer,String> map = new HashMap<>();
        map.put(VIEW_PCA,"实验数据降维散点图");
        map.put(VIEW_ATTRI,"单一属性类别分析");
        map.put(VIEW_MULATTRI,"二维属性类别分析");
        map.put(VIEW_REG_ATTRI,"单一属性预测分析");
        return map;
    }

    //单一属性故障分析
    public static EchatsOptions resloveAttribute(Instances instances, String attributeName){
        instances.setClassIndex(instances.numAttributes()-1);
        List<String> xData = new ArrayList<String>();
        double min = Float.MAX_VALUE;
        double max = Float.MIN_VALUE;
        Attribute attribute = instances.attribute(attributeName);
        Set<String> labels = new HashSet<>();
        for (int i=0;i<instances.size();i++){
            double key = instances.get(i).value(attribute);
            min = Math.min(min,key);
            max = Math.max(max,key);
            try {
                labels.add(instances.get(i).stringValue(instances.numAttributes()-1)+"");
            }catch (Exception e){
                labels.add(instances.get(i).value(instances.numAttributes()-1)+"");
            }
        }
        int step = INTERNAL/labels.size();
        double interval = (max-min)/step;
        for (int i=0;i<step;i++){
            xData.add(makeNumber(min+i*interval)+"");
        }

        EchatsOptions options = new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("",""));
        options.setLegend(new EchatsOptions.LegendBean(new ArrayList<String>(labels)));
        EchatsOptions.XAxisBean xAxisBean = new EchatsOptions.XAxisBean("category",true,new EchatsOptions.XAxisBean.AxisLabelBean());
        xAxisBean.setData(xData);
        options.setxAxis(Arrays.asList(new EchatsOptions.XAxisBean[]{xAxisBean}));
        options.setyAxis(Arrays.asList(new EchatsOptions.YAxisBean[]{new EchatsOptions.YAxisBean("value",true,new EchatsOptions.YAxisBean.AxisLabelBeanX())}));
        List<EchatsOptions.SeriesBean> seriesBeans = new ArrayList<EchatsOptions.SeriesBean>();

        Map<String,int[]> map = new HashMap<>();
        Map<String,List<Integer>> dataMap = new HashMap<>();
        for (String label:labels){
            EchatsOptions.SeriesBean normal = new EchatsOptions.SeriesBean();
            normal.setName(label);
            normal.setType("bar");
            normal.setItemStyle(new EchatsOptions.SeriesBean.ItemStyleBean(
                    new EchatsOptions.SeriesBean.ItemStyleBean.NormalBean("")));
            List<Integer> nData = new ArrayList<Integer>();
            int[] dArray = new int[step];
            Arrays.fill(dArray,0);
            normal.setData(nData);
            dataMap.put(label,nData);
            map.put(label,dArray);
            seriesBeans.add(normal);
        }

        options.setSeries(seriesBeans);

        for (Instance instance:instances){
            double key = instance.value(attribute);
            String classId = null;
            try {
                classId = instance.stringValue(instance.numAttributes()-1);
            }catch (Exception e){
                classId = instance.value(instance.numAttributes()-1)+"";
            }
            boolean isBread = false;
            for (int j=0;j<step;j++){
                if (key<Double.valueOf(xData.get(j))){
                    for (String label:labels){
                        if (classId.equals(label)){
                            map.get(label)[j]++;
                            isBread = true;
                            break;
                        }
                    }
                }
                if (isBread) break;
            }
        }
        for (String label:labels){
            List<Integer> list = dataMap.get(label);
            int[] array = map.get(label);
            for (int count:array){
                list.add(count);
            }
        }

        return options;
    }

    //二维属性故障分析
    public static EchatsOptions resloveMulAttribute(Instances instances,Map<String,Object> map) throws Exception {
        instances.setClassIndex(instances.numAttributes() - 1);

        EchatsOptions options = new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("", ""));
        options.setxAxis(Arrays.asList(new EchatsOptions.XAxisBean[]{new EchatsOptions.XAxisBean("value", true, new EchatsOptions.XAxisBean.AxisLabelBean())}));
        options.setyAxis(Arrays.asList(new EchatsOptions.YAxisBean[]{new EchatsOptions.YAxisBean("value", true, new EchatsOptions.YAxisBean.AxisLabelBeanX())}));

        List<EchatsOptions.SeriesBean> seriesBeans = new ArrayList<EchatsOptions.SeriesBean>();

        Set<String> labels = new HashSet<>();
        for (int i = 0; i < instances.size(); i++) {
            try {
                labels.add(instances.get(i).stringValue(instances.numAttributes() - 1));
            } catch (Exception e) {
                labels.add(instances.get(i).value(instances.numAttributes() - 1) + "");

            }
        }

        int step = 2 * labels.size();
        Map<String, List<List<Double>>> dataMap = new HashMap<>();
        for (String label : labels) {
            EchatsOptions.SeriesBean normal = new EchatsOptions.SeriesBean();
            normal.setName(label);
            normal.setType("scatter");
            normal.setItemStyle(new EchatsOptions.SeriesBean.ItemStyleBean(new EchatsOptions.SeriesBean.ItemStyleBean.NormalBean("")));
            List<List<Double>> nData = new ArrayList<List<Double>>();
            dataMap.put(label, nData);
            normal.setSymbolSize(4);
            normal.setData(nData);
            seriesBeans.add(normal);
        }
        options.setSeries(seriesBeans);
        options.setLegend(new EchatsOptions.LegendBean(new ArrayList<String>(labels)));

        Attribute attribute1 = instances.attribute(map.get("attribute1").toString());
        Attribute attribute2 = instances.attribute(map.get("attribute2").toString());

        for (int i = 0; i < instances.size(); i++) {
            if (i % step != 0) {
                continue;
            }
            Instance instance = instances.get(i);
            String classId = null;
            try {
                classId = instance.stringValue(instance.numAttributes() - 1);
            } catch (Exception e) {
                classId = instance.value(instance.numAttributes() - 1) + "";
            }
            for (String label : labels) {
                if (classId.equals(label)) {
                    dataMap.get(label).add(Arrays.asList(new Double[]{instance.value(attribute1), instance.value(attribute2)}));
                    break;
                }
            }
        }
        return options;
    }

    // pca降至三维
    public static EchartOptions3D reslovePCA3(Instances instances) throws Exception {
        instances.setClassIndex(instances.numAttributes()-1);
        EchartOptions3D options3D =new EchartOptions3D();
        PrincipalComponents pca = new PrincipalComponents();
        pca.setInputFormat(instances);
        pca.setOptions(new String[]{"-M","3"});
        Instances res = Filter.useFilter(instances,pca);


        List<double[]> data =new ArrayList<>();
        Attribute attribute = res.attribute(0);
        Attribute attribute1 = res.attribute(1);
        Attribute attribute2 = res.attribute(2);

        for (int i = 0; i < res.size(); i++) {
            double[] dataitem = new double[3];
            Instance instance = res.get(i);
            dataitem[0] = instance.value(attribute);
            dataitem[1] = instance.value(attribute1);
            dataitem[2] = instance.value(attribute2);
            data.add(dataitem);
        }

        options3D.setBackground("#fff");
        options3D.setxAxis3D(new EchartOptions3D.XAxisBean3D());
        options3D.setyAxis3D(new EchartOptions3D.YAxisBean3D());
        options3D.setzAxis3D(new EchartOptions3D.ZAxisBean3D());
        options3D.setGrid3D(new EchartOptions3D.Grid3DBean());
        options3D.setTooltip(new EchartOptions3D.TooltipBean3D());
        options3D.setSeries(new EchartOptions3D.SeriesBean3D(data));

        return options3D;
    }

    //pca降维 回归算法
    public static EchatsOptions resloveRegPCA(Instances instances,int step) throws Exception {
        instances.setClassIndex(instances.numAttributes() - 1);
        PrincipalComponents pca = new PrincipalComponents();
        pca.setInputFormat(instances);
        pca.setOptions(new String[]{"-M", "2"});
        Instances res = Filter.useFilter(instances, pca);

        EchatsOptions options = new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("",""));
        options.setxAxis(Arrays.asList(new EchatsOptions.XAxisBean[]{new EchatsOptions.XAxisBean("value",true,new EchatsOptions.XAxisBean.AxisLabelBean())}));
        options.setyAxis(Arrays.asList(new EchatsOptions.YAxisBean[]{new EchatsOptions.YAxisBean("value",true,new EchatsOptions.YAxisBean.AxisLabelBeanX())}));


        List<EchatsOptions.SeriesBean> seriesBeans = new ArrayList<EchatsOptions.SeriesBean>();
        Set<String> labels = new HashSet<>();
        options.setLegend(new EchatsOptions.LegendBean(new ArrayList<String>(labels)));

        Attribute attribute = res.attribute(0);
        Attribute attribute1 = res.attribute(1);

        List<double[]> dataPca = new ArrayList<double[]>();
        for (int i = 0; i < res.size(); i++) {
            if (i % step != 0) {
                continue;
            }
            double[] data = new double[2];
            Instance instance = res.get(i);
            data[0] = instance.value(attribute);
            data[1] = instance.value(attribute1);
            dataPca.add(data);
        }
        EchatsOptions.SeriesBean normal = new EchatsOptions.SeriesBean();
        normal.setType("scatter");
        normal.setItemStyle(new EchatsOptions.SeriesBean.ItemStyleBean(new EchatsOptions.SeriesBean.ItemStyleBean.NormalBean("")));
        normal.setSymbolSize(3);
        normal.setData(dataPca);
        seriesBeans.add(normal);
        options.setSeries(seriesBeans);
        return options;
    }

    //pca 降维 分类
    public static EchatsOptions reslovePCA(Instances instances) throws Exception {
        instances.setClassIndex(instances.numAttributes()-1);
        PrincipalComponents pca = new PrincipalComponents();
        pca.setInputFormat(instances);
        pca.setOptions(new String[]{"-M","2"});
        Instances res = Filter.useFilter(instances,pca);

        EchatsOptions options = new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("",""));
        options.setxAxis(Arrays.asList(new EchatsOptions.XAxisBean[]{new EchatsOptions.XAxisBean("value",true,new EchatsOptions.XAxisBean.AxisLabelBean())}));
        options.setyAxis(Arrays.asList(new EchatsOptions.YAxisBean[]{new EchatsOptions.YAxisBean("value",true,new EchatsOptions.YAxisBean.AxisLabelBeanX())}));

        List<EchatsOptions.SeriesBean> seriesBeans = new ArrayList<EchatsOptions.SeriesBean>();

        Set<String> labels = new HashSet<>();
        for (int i=0;i<res.size();i++){
            try {
                labels.add(res.get(i).stringValue(res.numAttributes()-1));
            }catch (Exception e){
                labels.add(res.get(i).value(res.numAttributes()-1)+"");

            }
        }
        options.setLegend(new EchatsOptions.LegendBean(new ArrayList<String>(labels)));
        int step = 2*labels.size();
        Map<String,List<List<Double>>> dataMap = new HashMap<>();
        for (String label:labels){
            EchatsOptions.SeriesBean normal = new EchatsOptions.SeriesBean();
            normal.setName(label);
            normal.setType("scatter");
            normal.setItemStyle(new EchatsOptions.SeriesBean.ItemStyleBean(new EchatsOptions.SeriesBean.ItemStyleBean.NormalBean("")));
            List<List<Double>> nData = new ArrayList<List<Double>>();
            dataMap.put(label,nData);
            normal.setSymbolSize(4);
            normal.setData(nData);
            seriesBeans.add(normal);
        }
        options.setSeries(seriesBeans);

        for (int i=0;i<res.size();i++){
            if (i%step != 0){
                continue;
            }
            Instance instance = res.get(i);
            String classId = null;
            try {
                classId = instance.stringValue(instance.numAttributes()-1);
            }catch (Exception e){
                classId = instance.value(instance.numAttributes()-1)+"";
            }
            for (String label:labels){
                if (classId.equals(label)){
                    dataMap.get(label).add(Arrays.asList(new Double[]{instance.value(0),instance.value(1)}));
                    break;
                }
            }
        }
        return options;
    }


    //特征相关性分析
    public static EchatsOptions resloveRelative(String file) throws Exception {
        String cmd = "python "+Const.RELATIVE_CMD+" path="+file;
        System.out.println("现做"+cmd);
        String res = PythonUtils.execPy(cmd);
        Gson gson = new Gson();
        RelativeVo vo = gson.fromJson(res,RelativeVo.class);
        EchatsOptions options = new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("相关性分析","表征特征的重要程度"));
        options.setTooltip(new EchatsOptions.TooltipBean());
        EchatsOptions.XAxisBean xAxisBean = new EchatsOptions.XAxisBean("value",true,null);
        options.setxAxis(Arrays.asList(xAxisBean));
        EchatsOptions.YAxisBean yAxisBean = new EchatsOptions.YAxisBean("category",true,null);
        yAxisBean.setData(vo.getAttributeName());
        options.setyAxis(Arrays.asList(yAxisBean));
        options.setLegend(new EchatsOptions.LegendBean(Arrays.asList("快速特征选择")));
        EchatsOptions.SeriesBean seriesBean = new EchatsOptions.SeriesBean();
        seriesBean.setType("bar");
        seriesBean.setName("快速特征选择");
        options.setSeries(Arrays.asList(seriesBean));
        seriesBean.setData(vo.getRelativeValue());
        return options;
    }

    //特征相关性分析回归
    public static EchatsOptions resloveRegRelative(String file) throws Exception {
        String cmd = "python "+Const.REG_RELATIVE_CMD+" path="+file;
        String res = PythonUtils.execPy(cmd);
        if(res.isEmpty()){
            return null;
        }
        Gson gson = new Gson();
        RelativeVo vo = gson.fromJson(res,RelativeVo.class);
        EchatsOptions options = new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("相关性分析","表征特征的重要程度"));
        options.setTooltip(new EchatsOptions.TooltipBean());
        EchatsOptions.XAxisBean xAxisBean = new EchatsOptions.XAxisBean("value",true,null);
        options.setxAxis(Arrays.asList(xAxisBean));
        EchatsOptions.YAxisBean yAxisBean = new EchatsOptions.YAxisBean("category",true,null);
        yAxisBean.setData(vo.getAttributeName());
        options.setyAxis(Arrays.asList(yAxisBean));
        options.setLegend(new EchatsOptions.LegendBean(Arrays.asList("快速特征选择")));
        EchatsOptions.SeriesBean seriesBean = new EchatsOptions.SeriesBean();
        seriesBean.setType("bar");
        seriesBean.setName("快速特征选择");
        options.setSeries(Arrays.asList(seriesBean));
        seriesBean.setData(vo.getRelativeValue());
        return options;
    }


    public static EchatsOptions resloveRegAttribute (Instances instances, String attributeName){
        EchatsOptions options = new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("属性与结果分布关系",""));
        options.setTooltip(new EchatsOptions.TooltipBean());
        options.setxAxis(Arrays.asList(new EchatsOptions.XAxisBean[]{new EchatsOptions.XAxisBean("value",true,new EchatsOptions.XAxisBean.AxisLabelBean())}));
        options.setyAxis(Arrays.asList(new EchatsOptions.YAxisBean[]{new EchatsOptions.YAxisBean("value",true,new EchatsOptions.YAxisBean.AxisLabelBeanX())}));
        options.setLegend(new EchatsOptions.LegendBean(Arrays.asList(attributeName)));
        EchatsOptions.SeriesBean seriesBean = new EchatsOptions.SeriesBean();
        seriesBean.setType("scatter");
        seriesBean.setName(attributeName);
        seriesBean.setSymbolSize(4);
        seriesBean.setItemStyle(new EchatsOptions.SeriesBean.ItemStyleBean(
                new EchatsOptions.SeriesBean.ItemStyleBean.NormalBean("#ff0000")));
        List<double[]> data = new ArrayList<>();
        Attribute attribute= instances.attribute(attributeName);
        Attribute attribute1= instances.attribute(instances.numAttributes()-1);
        for(int i = 0; i<instances.size();i++){
            double [] dataIn = new double[2];
            Instance instance = instances.get(i);
            dataIn[0]= instance.value(attribute);
            dataIn[1]= instance.value(attribute1);
            data.add(dataIn);
        }
        seriesBean.setData(data);
        options.setSeries(Arrays.asList(seriesBean));
        return options;
    }




    private static double makeNumber(double d){
        DecimalFormat df = new DecimalFormat("######0.000");
        return new Double(df.format(d));
    }


    public static String createColor()
    {
        //红色
        String red;
        //绿色
        String green;
        //蓝色
        String blue;
        //生成随机对象
        Random random = new Random();
        //生成红色颜色代码
        red = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成绿色颜色代码
        green = Integer.toHexString(random.nextInt(256)).toUpperCase();
        //生成蓝色颜色代码
        blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

        //判断红色代码的位数
        red = red.length()==1 ? "0" + red : red ;
        //判断绿色代码的位数
        green = green.length()==1 ? "0" + green : green ;
        //判断蓝色代码的位数
        blue = blue.length()==1 ? "0" + blue : blue ;
        //生成十六进制颜色值
        String color = "#"+red+green+blue;
        return color;
    }


    public static void main(String[] args) throws Exception {
        Instances instances = WekaUtils.readFromFile("F:/plantdata.csv");
        EchatsOptions options = reslovePCA(instances);
        Gson gson = new Gson();
        System.out.println(gson.toJson(options));
    }
}

// 降维 在二维、三维可视化