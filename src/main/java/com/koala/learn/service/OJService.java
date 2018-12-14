package com.koala.learn.service;

import com.koala.learn.dao.SqlTestMapper;
import com.koala.learn.dao.CourseTypeMapper;
import com.koala.learn.dao.HtmlTestMapper;
import com.koala.learn.entity.CourseType;
import com.koala.learn.entity.HtmlTest;
import com.koala.learn.entity.SqlTestWithBLOBs;
import com.koala.learn.vo.OJGroupVo;
import com.koala.learn.vo.OJVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OJService {

    @Autowired
    SqlTestMapper sqlTestMapper;

    @Autowired
    CourseTypeMapper courseTypeMapper;

    @Autowired
    HtmlTestMapper htmlTestMapper;

    public List<CourseType> selectAllOj(){
        return courseTypeMapper.selectAllOJType();
    }

    public List<OJGroupVo> getVo(int typeId){
       switch (typeId){
           default:
               return getHtmlVo(typeId);
           case 1:
               return getSqlVo(typeId);

       }
    }

    private List<OJGroupVo> getHtmlVo(int typeId){
        List<HtmlTest> testList = htmlTestMapper.selectByType(typeId);
        CourseType courseType = courseTypeMapper.selectByPrimaryKey(typeId);
        List<OJGroupVo> groupVos = new ArrayList<>();
        String type = testList.get(0).getType();
        OJGroupVo vo = new OJGroupVo(type,new ArrayList<OJVo>());
        for (HtmlTest htmlTest:testList){
            if (htmlTest.getType().equals(type)){
                OJVo oj = new OJVo(htmlTest.getTitle(),courseType.getCover(),"/oj/"+typeId+"/"+htmlTest.getId());
                vo.getVos().add(oj);
            }else {
                groupVos.add(vo);
                type = htmlTest.getType();
                vo = new OJGroupVo(type,new ArrayList<OJVo>());
                OJVo oj = new OJVo(htmlTest.getTitle(),courseType.getCover(),"/oj/"+typeId+"/"+htmlTest.getId());
                vo.getVos().add(oj);
            }
        }
        groupVos.add(vo);
        return groupVos;
    }

    private List<OJGroupVo> getSqlVo(int typeId){
        List<SqlTestWithBLOBs> testList = sqlTestMapper.selectAll();
        CourseType courseType = courseTypeMapper.selectByPrimaryKey(typeId);
        List<OJGroupVo> groupVos = new ArrayList<>();
        OJGroupVo vo = new OJGroupVo("MySQL练习",new ArrayList<OJVo>());
        groupVos.add(vo);
        for (SqlTestWithBLOBs sql:testList){
            OJVo oj = new OJVo(sql.getName(),courseType.getCover(),"/oj/mysql/"+sql.getId());
            vo.getVos().add(oj);
        }
        return groupVos;
    }
    public SqlTestWithBLOBs getSqlTest(Integer testId){
        return sqlTestMapper.selectByPrimaryKey(testId);
    }

    public HtmlTest getHtmlCourseTest(Integer testId){
        return htmlTestMapper.selectByPrimaryKey(testId);
    }

    public List<String> resolveTableHead(List<Map<String,String>> maps){
        Map<String,String> map = maps.get(0);
        return new ArrayList<>(map.keySet());
    }

    public List<List<String>> resolveTableBody(List<Map<String,String>> maps,List<String> headers){
        List<List<String>> res = new ArrayList<>();
        for (Map<String,String> map:maps){
            List<String> line = new ArrayList<>();
            for (String header:headers){
                line.add(map.get(header));
            }
            res.add(line);
        }
        return res;
    }

}
