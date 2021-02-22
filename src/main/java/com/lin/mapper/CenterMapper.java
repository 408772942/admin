package com.lin.mapper;

import com.lin.model.Student;
import com.lin.model.Subject;
import com.lin.model.Teacher;
import com.lin.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface CenterMapper {
    Integer subjectAdd(Integer subjectid,String subjectname,Integer teacherid);
    Integer subjectSelect(Integer subjectid);
    Integer subjectDel(Integer subjectid);
    void signDel(Integer subjectid);
    List<Subject>subjectAll();
    Student student(Integer stuid);
    List<Student>studentAll();
    Teacher teacherSelect(Integer teacherid);
    Teacher teacherSelectByName(String teachername);
    List<Teacher>teacherAll();
    List<Teacher>teacherBy(Integer userid);
    Teacher self(Integer userid);
    Integer studentAdd(Integer stuid,String name,String classname,Integer num,String sex,String birthday,Integer phone,String addr);
    Integer passwd(Integer userid,String password);
    List<User>teacher();
    //全部课程
    List<Subject>stuId(Integer subjectid);
    //课程教师名
    String teacherName(Integer teacherid);
    //课程数
    Integer count(Integer subjectid);
    //课程内学生
    List<Subject>studentSubAll(Integer subjetctid);
    //名字
    String subjectName(Integer subjectid);
    //状态
    Integer subjectState(Integer subjectid);
    //教师号
    Integer teacherid(Integer subjectid);
    //删除学生
    Integer studentSubDel(Integer subjectid,Integer stuid);
    //班级人数
    Integer classcount(String classname);
    //班级
    List<Student>classAll();
    //班级名-学生
    List<Student>studentAllByClass(String classname);
    //查看班级名
    String classname(Integer stuid);
    //删除学生
    void studentDelByClass(Integer stuid);
    void signDelByClass(Integer stuid);
    void userDelByClass(Integer stuid);
    //添加登陆
    Integer userAddByClass(Integer userid,String password,String name);
    //删除课程-学生
    void classDel(String classname);
    //添加班级
    Integer classAdd(String classname);
}
