package com.lin.mapper;

import com.lin.model.Student;
import com.lin.model.Subject;
import com.lin.model.Score;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface UserMapper {
    void sign(Integer userid,Integer subjectid);
    List<Score> score(Integer userid);
    List<Subject> subject(Integer userid);
    List<Subject> subjectSign(Integer userid);
    Integer signReady(Integer userid,Integer subjectid);
    Integer passwd(Integer userid,String password);
    String teacherName(Integer teacherid);
    Student material(Integer stuid);
}