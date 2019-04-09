package com.koala.learn.controller;


import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.entity.EchatsOptions;
import com.koala.learn.service.WxComponentService;
import com.koala.learn.utils.treat.ViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.supervised.instance.SMOTE;

import java.io.File;
import java.io.FileReader;


@Controller
@RequestMapping("/wx/component/")
public class WxLabComponentController {



    @Autowired
    WxComponentService wxComponentService;

    @RequestMapping(value = "init_smote")
    @ResponseBody
    public ServerResponse<EchatsOptions> getSmoteData(){
        EchatsOptions options =new EchatsOptions();
        try {
            options= ViewUtils.reslovePCA(new Instances(new FileReader(Const.DATA_FOR_SMOTE)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ServerResponse.createBySuccess(options);
    }

    @RequestMapping(value = "get_smote")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleSmote(@RequestParam(value = "k_neighbors",defaultValue = "5") Integer kNeighbors,
                                                     @RequestParam(value = "ratio",defaultValue = "100")Integer ratio){

        EchatsOptions echatsOptions =new EchatsOptions();

        try {
            Instances instances = new Instances(new FileReader(Const.DATA_FOR_SMOTE));
            instances.setClassIndex(instances.numAttributes() - 1);
            SMOTE smote =new SMOTE();
            String[] options = {"-S", String.valueOf(1), "-P", String.valueOf(ratio), "-K", String.valueOf(kNeighbors)};
            smote.setInputFormat(instances);
            smote.setOptions(options);
            Filter.useFilter(instances, smote);
            echatsOptions=ViewUtils.reslovePCA(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ServerResponse.createBySuccess(echatsOptions);
    }


    @RequestMapping("get_oneclasssvm")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleOneClassSVM(@RequestParam(value = "nu",defaultValue = "0.003") Double nu) throws Exception {

        File input =new File(Const.DATA_FOR_OCSVM);

        File out=wxComponentService.handleOcSvm(nu,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options =ViewUtils.reslovePCA(instances);

        return ServerResponse.createBySuccess(options);

    }

    @RequestMapping("get_isolation")
    @ResponseBody
    public ServerResponse<EchatsOptions> handleIsoLationForest(@RequestParam(value = "contamination",defaultValue = "0.003") Double contamination) throws Exception {

        File input =new File(Const.DATA_FOR_ISOLATIONFOREST);

        File out=wxComponentService.handleIsolation(contamination,input);

        Instances instances=new Instances(new FileReader(out.getAbsolutePath()));

        EchatsOptions options =ViewUtils.reslovePCA(instances);

        return ServerResponse.createBySuccess(options);

    }


}
