package com.lin.mapper;

import com.lin.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface TeacherMapper {
    List<Subject> subjectTeacher(Integer userid);
    void signOpen(Integer userid,Integer subjectid);
    void signOff(Integer userid,Integer subjectid);
    List<Subject> signOpenAll(Integer userid);
    List<Subject> signOffAll(Integer userid);
    void cleanSign(Integer subjectid);
    Student student(Integer userid);
    List<Sign> signAll(Integer subjectid);
    String stuName(Integer userid);
    Integer subjectState(Integer subjectid,Integer userid);
    String subjectName(Integer subjectid,Integer userid);
    Integer subjectAdd(Integer subjectid,String subjectname,Integer stuid,Integer userid,Integer state);
    Integer studentDel(Integer subjectid,Integer userid,Integer stuid);
    List<Subject>stuId(Integer subjectid,Integer userid);
    Student studentAll(Integer stuid);
    Integer studentAddReady(Integer stuid,Integer subjectid);
    Student selectById(Integer stuid);
    Student selectByName(String name);
    Student selectByNameClass(String name,String classname);
    List<Student> selectByClass(String classname);
    Integer passwd(Integer userid,String password);
    Integer studentCount(Integer subjectid);
    List<Subject>subject(Integer userid);
    List<String>classname();
    List<Student>studentShow(String classname,Integer subjectid);
    Integer studentAllDel(Integer subjectid);
    Score score(Integer subjectid,Integer stuid);
    void scoreDel(Integer subjectid,Integer stuid);
    void scoreAdd(Integer subjectid,String subjectname,Integer score,Integer stuid);
    Teacher material(Integer teacherid);
}