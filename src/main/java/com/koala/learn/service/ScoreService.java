package com.koala.learn.service;

import com.koala.learn.Const;
import com.koala.learn.commen.ServerResponse;
import com.koala.learn.dao.ScoreMapper;
import com.koala.learn.entity.Score;
import com.koala.learn.entity.User;
import com.koala.learn.utils.PythonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class ScoreService {

    @Autowired
    LabDesignBGService labDesignBGService;

    @Autowired
    ScoreMapper scoreMapper;


    private static final Logger logger= LoggerFactory.getLogger(ScoreService.class);

    public List<Score> getScoreListByLabId(Integer labId){
        return scoreMapper.selectAllScoreDescByLabId(labId);
    }

    public List<Score> getScoreListByUserId(Integer userId){
        return scoreMapper.selectAllByUserId(userId);
    }


    public ServerResponse doUpload(Integer resultLabName, Integer groupId, MultipartFile resultFile, User user) throws IOException {

        String scoreFile = getScoreFileByresultLabName(resultLabName);


        //保存结果文件
        String resultFileName=resultFile.getOriginalFilename();
        String newFileName=labDesignBGService.getFileName("score",resultFileName);
        File resultCsv = new File(Const.UPDATE_CLASS_ROOT_SCORE,newFileName);
        resultFile.transferTo(resultCsv);

        StringBuilder sb = new StringBuilder("python ");
        if (scoreFile.contains(",")){
            String[] scoreFiles = scoreFile.split(",");
            sb.append(scoreFiles[0]).append(" ");
            sb.append("studentcsv=").append(resultCsv.getAbsolutePath()).append(" ");
            sb.append("teachercsv=").append(scoreFiles[1]);
        }else {
            sb.append(scoreFile).append(" ");
            sb.append("file=").append(resultCsv.getAbsolutePath());
        }

        logger.info("开始测试上传的实验结果，python语句为："+sb.toString());
        String res = PythonUtils.execPy(sb.toString());
        logger.info("得到结果："+res);

        String[] result=res.split(" ");
        Double finalScore;
        Double accscore;
        Double precisionscore;
        Double recallscore;
        try{
            finalScore=Double.parseDouble(result[0]);
            accscore=Double.parseDouble(result[1]);
            precisionscore=Double.parseDouble(result[2]);
            recallscore=Double.parseDouble(result[3]);
        }catch (Exception e){
            return ServerResponse.createByErrorMessage("上传数据错误，请检查格式是否正确~");
        }
        Score score=new Score();
        score.setLabId(resultLabName);
        score.setLabName(getLabNameByresultLabName(resultLabName));
        score.setUserId(user.getId());
        score.setGroupId(groupId);
        score.setFinalScore(finalScore);
        score.setAccscore(accscore);
        score.setPrecisionscore(precisionscore);
        score.setRecallscore(recallscore);
        score.setCreatTime(new Date());
        score.setUpdateTime(new Date());

        int count=scoreMapper.insert(score);

        if(count<=0){
            return ServerResponse.createByErrorMessage("保存得分信息失败~");
        }

        return ServerResponse.createBySuccess(res);
    }

    public String getScoreFileByresultLabName(Integer resultLabName){
        if(resultLabName.equals(1)){
            return Const.FILE_SCORE_CWRU;
        }
        if(resultLabName.equals(2)){
            return Const.FILE_SCORE_CWRU_2;
        }
        if (resultLabName.equals(3)){
            return Const.FILE_SCORE_PARDENBORN+","+Const.FILE_SOCRE_CSV;
        }
        return null;
    }


    public String getLabNameByresultLabName(Integer resultLabName){
        if(resultLabName.equals(1)){
            return Const.NAME_LAB_CWRU;
        }
        if(resultLabName.equals(2)){
            return Const.NAME_LAB_CWRU_2;
        }
        if (resultLabName.equals(3)){
            return Const.NAME_LAB_PADERNBORN;
        }
        return null;
    }

    public Score getScoreById(Integer scoreId) {

        return scoreMapper.selectById(scoreId);
    }

    public int deleteById(Integer scoreId) {
        return scoreMapper.deleteById(scoreId);
    }

    public List<Score> getScoreListByDate(Integer userId,Date date) {
        return scoreMapper.selectAllByUserIdAndDate(userId,date);
    }
}
