package com.koala.learn.service;

import com.google.gson.Gson;
import com.koala.learn.Const;
import com.koala.learn.component.JedisAdapter;
import com.koala.learn.dao.AlgorithmMapper;
import com.koala.learn.entity.Algorithm;
import com.koala.learn.entity.EchatsOptions;
import com.koala.learn.utils.RedisKeyUtil;
import com.koala.learn.utils.treat.WxViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class AlgorithmService {

    @Autowired
    AlgorithmMapper algorithmMapper;

    @Autowired
    JedisAdapter mJedisAdapter;

    @Autowired
    WxComponentService wxComponentService;

    public Algorithm getAlgoById(Integer id){
        return algorithmMapper.getById(id);
    }

    public List<Algorithm> getAllAlgos(){
        return  algorithmMapper.selectAllAlgorithm();
    }
    public String initSmoteEchart(){
        String key = RedisKeyUtil.getInitSmote();
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        try {
            Instances instances = new Instances(new FileReader(Const.DATA_FOR_SMOTE));
            EchatsOptions options = WxViewUtils.reslovePCA(instances, 12);
            Gson gson = new Gson();
            String optionsCache = gson.toJson(options);
            mJedisAdapter.set(key, optionsCache);
            return optionsCache;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String initIsolationForestEchart() throws Exception {
        String key=RedisKeyUtil.getIsolationKey();
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        File input =new File(Const.DATA_FOR_INIT_ISOLATIONFOREST);
        Instances instances=new Instances(new FileReader(input));
        EchatsOptions options =WxViewUtils.reslovePCA(instances,1);
        Gson gson=new Gson();
        String cacheOptions=gson.toJson(options);
        mJedisAdapter.set(key,cacheOptions);

        return cacheOptions;
    }

    public String getSmoteEchart(Map<String,Object> param) throws Exception {
        Integer ratio= Integer.parseInt((String) param.get("-P"));
        Integer kNeighbors=5;
        if(ratio<=0){
            return initSmoteEchart();
        }
        String key=RedisKeyUtil.getSmoteKey(kNeighbors,ratio);
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        Instances instances = new Instances(new FileReader(Const.DATA_FOR_SMOTE));
        instances.setClassIndex(instances.numAttributes() - 1);
        SMOTE smote =new SMOTE();
        String[] options = {"-S", String.valueOf(1), "-P", String.valueOf(ratio), "-K", String.valueOf(kNeighbors)};
        smote.setInputFormat(instances);
        smote.setOptions(options);
        Instances instances1 = Filter.useFilter(instances, smote);
        EchatsOptions echatsOptions = WxViewUtils.reslovePCA(instances1,12);
        Gson gson = new Gson();
        String cacheOptions=gson.toJson(echatsOptions);
        mJedisAdapter.set(key,cacheOptions);
        return cacheOptions;
    }

    public String getIsoForestEchart(Map<String, Object> param) throws Exception {
        Double con=Double.parseDouble((String) param.get("-i"));
        if(con<0 || con>1){
            return null;
        }
        String key=RedisKeyUtil.getIsolationKey(con);

        String cache=mJedisAdapter.get(key);
        Gson gson=new Gson();

        if(cache!=null){
            return cache;
        }
        File input =new File(Const.DATA_FOR_ISOLATIONFOREST);

        File out=wxComponentService.handleIsolation(con,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options =WxViewUtils.reslovePCA(instances,1);

        String cacheOptions=gson.toJson(options);

        mJedisAdapter.set(key,cacheOptions);

        return cacheOptions;
    }

    public String initNormalizationEchart() throws Exception {
        String key=RedisKeyUtil.getNormalizationKey();
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        EchatsOptions options= WxViewUtils.resloveNormalization(new Instances(new FileReader(Const.DATA_FOR_NORMALIZATION)));
        options.setTitle(new EchatsOptions.TitleBean("归一化前数据分布","以每个特征的样本均值表示"));
        Gson gson=new Gson();
        String cacheOptions=gson.toJson(options);
        mJedisAdapter.set(key,cacheOptions);
        return cacheOptions;
    }

    public String getNormalizationEchart() throws Exception {
        String key=RedisKeyUtil.getNormalizationKey("(0,1)");
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        File input =new File(Const.DATA_FOR_NORMALIZATION);
        File out=wxComponentService.handleNormalization(input);
        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
        EchatsOptions options =WxViewUtils.resloveNormalization(instances);
        options.setTitle(new EchatsOptions.TitleBean("归一化后数据分布","以每个特征的样本均值表示"));
        Gson gson=new Gson();
        String cacheOptions=gson.toJson(options);
        mJedisAdapter.set(key,cacheOptions);
        return cacheOptions;
    }

    public String getFFTEchart(Map<String, Object> param) throws IOException {
        String attribute= (String) param.get("-a");
        String key=RedisKeyUtil.getfftKey(attribute);
        String cache=mJedisAdapter.get(key);
        if(cache!=null){
            return cache;
        }
        File input =new File(Const.DATA_FOR_FFT_WX);
        File out=wxComponentService.handleFFT(attribute,input);
        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));
        EchatsOptions options=WxViewUtils.resloveFFT(instances,1);
        Gson gson=new Gson();
        String cacheOptions=gson.toJson(options);
        mJedisAdapter.set(key,cacheOptions);
        return cacheOptions;
    }
}
