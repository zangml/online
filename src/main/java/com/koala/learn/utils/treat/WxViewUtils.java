package com.koala.learn.utils.treat;
import com.koala.learn.entity.EchatsOptions;
import java.util.*;
import java.util.List;


import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.PrincipalComponents;

public class WxViewUtils {

    //微信调用二维属性分析
    public static EchatsOptions resloveMulAttribute12Step(Instances instances,Map<String,Object> map) throws Exception {
        instances.setClassIndex(instances.numAttributes()-1);

        EchatsOptions options = new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("",""));
        options.setXAxis(Arrays.asList(new EchatsOptions.XAxisBean[]{new EchatsOptions.XAxisBean("value",true,new EchatsOptions.XAxisBean.AxisLabelBean())}));
        options.setYAxis(Arrays.asList(new EchatsOptions.YAxisBean[]{new EchatsOptions.YAxisBean("value",true,new EchatsOptions.YAxisBean.AxisLabelBeanX())}));

        List<EchatsOptions.SeriesBean> seriesBeans = new ArrayList<EchatsOptions.SeriesBean>();

        Set<String> labels = new HashSet<>();
        for (int i=0;i<instances.size();i++){
            try {
                labels.add(instances.get(i).stringValue(instances.numAttributes()-1));
            }catch (Exception e){
                labels.add(instances.get(i).value(instances.numAttributes()-1)+"");

            }
        }

        int step = 6*labels.size();
        Map<String,List<List<Double>>> dataMap = new HashMap<>();
        for (String label:labels){
            EchatsOptions.SeriesBean normal = new EchatsOptions.SeriesBean();
            normal.setName(label);
            normal.setType("scatter");
            normal.setItemStyle(new EchatsOptions.SeriesBean.ItemStyleBean(new EchatsOptions.SeriesBean.ItemStyleBean.NormalBean("")));
            List<List<Double>> nData = new ArrayList<List<Double>>();
            dataMap.put(label,nData);
            normal.setSymbolSize(3);
            normal.setData(nData);
            seriesBeans.add(normal);
        }
        options.setSeries(seriesBeans);
        options.setLegend(new EchatsOptions.LegendBean(new ArrayList<>(labels)));

        Attribute attribute1 = instances.attribute(map.get("attribute1").toString());
        Attribute attribute2 = instances.attribute(map.get("attribute2").toString());

        for (int i=0;i<instances.size();i++){
            if (i%step != 0){
                continue;
            }
            Instance instance = instances.get(i);
            String classId = null;
            try {
                classId = instance.stringValue(instance.numAttributes()-1);
            }catch (Exception e){
                classId = instance.value(instance.numAttributes()-1)+"";
            }
            for (String label:labels){
                if (classId.equals(label)){
                    dataMap.get(label).add(Arrays.asList(new Double[]{instance.value(attribute1),instance.value(attribute2)}));
                    break;
                }
            }
        }
        return options;
    }


    //微信调用fft频谱

    public static EchatsOptions resloveFFT(Instances instances){
        EchatsOptions options=new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("fft频谱图",""));
        options.setXAxis(Arrays.asList(new EchatsOptions.XAxisBean[]{new EchatsOptions.XAxisBean("频率/Hz","value",true,new EchatsOptions.XAxisBean.AxisLabelBean())}));
        options.setYAxis(Arrays.asList(new EchatsOptions.YAxisBean[]{new EchatsOptions.YAxisBean("幅值","value",true,new EchatsOptions.YAxisBean.AxisLabelBeanX())}));


        List<EchatsOptions.SeriesBean> seriesBeans = new ArrayList<EchatsOptions.SeriesBean>();
        Set<String> labels = new HashSet<>();
        options.setLegend(new EchatsOptions.LegendBean(new ArrayList<String>(labels)));

        Attribute attribute = instances.attribute(0);
        Attribute attribute1 = instances.attribute(1);

        List<double[]> dataFFT = new ArrayList<>();
        for (int i = 0; i < instances.size(); i++) {
            double[] data = new double[2];
            Instance instance = instances.get(i);
            data[0] = instance.value(attribute);
            data[1] = (instance.value(attribute1))*2;
            if(data[0]>1000) {
                dataFFT.add(data);
            }
        }
        EchatsOptions.SeriesBean normal = new EchatsOptions.SeriesBean();
        normal.setType("line");
        normal.setItemStyle(new EchatsOptions.SeriesBean.ItemStyleBean(new EchatsOptions.SeriesBean.ItemStyleBean.NormalBean("")));
        normal.setData(dataFFT);
        seriesBeans.add(normal);
        options.setSeries(seriesBeans);
        return options;

    }


    public static EchatsOptions reslovePCA(Instances instances,int step) throws Exception {
        instances.setClassIndex(instances.numAttributes()-1);
        PrincipalComponents pca = new PrincipalComponents();
        pca.setInputFormat(instances);
        pca.setOptions(new String[]{"-M","2"});
        Instances res = Filter.useFilter(instances,pca);

        EchatsOptions options = new EchatsOptions();
        options.setTitle(new EchatsOptions.TitleBean("",""));
        options.setXAxis(Arrays.asList(new EchatsOptions.XAxisBean[]{new EchatsOptions.XAxisBean("value",true,new EchatsOptions.XAxisBean.AxisLabelBean())}));
        options.setYAxis(Arrays.asList(new EchatsOptions.YAxisBean[]{new EchatsOptions.YAxisBean("value",true,new EchatsOptions.YAxisBean.AxisLabelBeanX())}));

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
        Map<String,List<List<Double>>> dataMap = new HashMap<>();
        for (String label:labels){
            EchatsOptions.SeriesBean normal = new EchatsOptions.SeriesBean();
            normal.setName(label);
            normal.setType("scatter");
            normal.setItemStyle(new EchatsOptions.SeriesBean.ItemStyleBean(new EchatsOptions.SeriesBean.ItemStyleBean.NormalBean("")));
            List<List<Double>> nData = new ArrayList<List<Double>>();
            dataMap.put(label,nData);
            normal.setSymbolSize(5);
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

}

// 降维 在二维、三维可视化