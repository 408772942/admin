package com.lin.service;

import com.lin.mapper.TeacherMapper;
import com.lin.mapper.UserMapper;
import com.lin.model.Score;
import com.lin.model.Sign;
import com.lin.model.Student;
import com.lin.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.management.VMOptionCompositeData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    public String signPage(HttpServletRequest request, Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        List<Subject> subject = teacherMapper.subjectTeacher(userid);
        model.addAttribute("subject", subject);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "teacher/sign";
    }

    public String signOpen(HttpServletRequest request, Integer subjectid,Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        teacherMapper.signOpen(userid, subjectid);
        teacherMapper.cleanSign(subjectid);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "redirect:/teacher/sign";
    }

    public String signOff(HttpServletRequest request, Integer subjectid) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        teacherMapper.signOff(userid, subjectid);
        return "redirect:/teacher/sign";
    }

    public String signOpenAll(HttpServletRequest request, Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        List<Subject> signOpenAll = teacherMapper.signOpenAll(userid);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("subject", signOpenAll);
        return "teacher/sign";
    }

    public String signOffAll(HttpServletRequest request, Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        List<Subject> signOffAll = teacherMapper.signOffAll(userid);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("subject", signOffAll);
        return "teacher/sign";
    }
    //签到查看
    public String signAll(HttpServletRequest request, Model model, Integer subjectid) {
        List<Sign> signAll = teacherMapper.signAll(subjectid);
        for (Sign sign : signAll) {
            sign.setStudentname(teacherMapper.stuName(sign.getUserid()));
        }
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("sign", signAll);
        return "teacher/studentSign";
    }
    //所有课程
    public String subject(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        List<Subject>subject=teacherMapper.subject(userid);
        for (Subject subjects : subject) {
           subjects.setStudentcount(teacherMapper.studentCount(subjects.getSubjectid()));
        }
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("subject",subject);
        return "teacher/subject";
    }
    //选择添加学生的班级
    public String add(HttpServletRequest request,Model model){
        List<String>classname=teacherMapper.classname();
        model.addAttribute("classname",classname);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "teacher/class";
    }
    //选择班级的所有学生
    public String studentShow(HttpServletRequest request,Model model){
        HttpSession session = request.getSession(true);
        Integer subjectid= (Integer) session.getAttribute("subjectid");
        String classname=request.getParameter("class");
        session.setAttribute("class",classname);
        List<Student>studentShow=teacherMapper.studentShow(classname,subjectid);
        for (Student student:studentShow){
            student.setSubjectid(subjectid);
        }
        model.addAttribute("student",studentShow);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "teacher/studentAdd";
    }

    public String studentAdd(HttpServletRequest request, Integer subjectid, Integer stuid,Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        String subjectName = teacherMapper.subjectName(subjectid, userid);
        Integer subjectState = teacherMapper.subjectState(subjectid, userid);
        if (teacherMapper.studentAddReady(stuid, subjectid) == null){
            if (teacherMapper.subjectAdd(subjectid, subjectName, stuid, userid, subjectState) == 1) {
                return "redirect:/teacher/studentAll/"+subjectid;
            } else {
                model.addAttribute("result","添加失败");
                return "final";
            }
        }else{
            model.addAttribute("result","学生已存在");
            return "final";
        }
    }

    public String studentAllAdd(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        HttpSession session = request.getSession(true);
        Integer subjectid= (Integer) session.getAttribute("subjectid");
        String classname= (String) session.getAttribute("class");
        List<Student>studentShow=teacherMapper.studentShow(classname,subjectid);
        String subjectName = teacherMapper.subjectName(subjectid, userid);
        Integer subjectState = teacherMapper.subjectState(subjectid, userid);
        for (Student student:studentShow){
            student.setSubjectid(subjectid);
            teacherMapper.subjectAdd(subjectid, subjectName, student.getStuid(), userid, subjectState);
        }
        return "redirect:/teacher/studentAll/"+subjectid;
    }

    public String studentAllDel(HttpServletRequest request,Model model){
        HttpSession session = request.getSession(true);
        Integer subjectid= (Integer) session.getAttribute("subjectid");
        if(teacherMapper.studentAllDel(subjectid)!=0){
            return "redirect:/teacher/studentAll/"+subjectid;
        }else {
            model.addAttribute("result","删除失败");
            return "final";
        }
    }

    public String studentDel(HttpServletRequest request, Integer stuid, Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        HttpSession session = request.getSession(true);
        Integer subjectid= (Integer) session.getAttribute("subjectid");
        if (teacherMapper.studentDel(subjectid, userid, stuid) == 1) {
            return "redirect:/teacher/studentAll/"+subjectid;
        }
        model.addAttribute("result","删除失败");
        return "final";
    }

    public String studentAll(HttpServletRequest request, Model model, Integer subjectid) {
        HttpSession session = request.getSession(true);
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        session.setAttribute("subjectid",subjectid);
        List<Subject> stu = teacherMapper.stuId(subjectid, userid);
        List<Student> studentAll = new ArrayList<>();
        for (Subject stuId : stu) {
            studentAll.add(teacherMapper.studentAll(stuId.getStuid()));
        }
        for (Student student:studentAll){
            student.setSubjectid(subjectid);
        }
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("student", studentAll);
        return "teacher/studentDel";
    }

    public String select(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "teacher/selectPage";
    }

    public String studentSelect(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        String userid= request.getParameter("userid");
        String name= request.getParameter("name");
        String classname=request.getParameter("classname");
        Student student;
        List<Student>students;
        if(!userid.isEmpty()){
            Integer id=Integer.valueOf(userid);
            student=teacherMapper.selectById(id);
            model.addAttribute("student",student);
        }else if(!name.isEmpty()){
            student=teacherMapper.selectByName(name);
            model.addAttribute("student",student);
        }else if(!classname.isEmpty()){
            students=teacherMapper.selectByClass(classname);
            model.addAttribute("student",students);
        }else if(!classname.isEmpty()&&!name.isEmpty()){
            student=teacherMapper.selectByNameClass(name,classname);
            model.addAttribute("student",student);
        }else {
            model.addAttribute("result","输入错误");
            return "final";
        }
        return "teacher/student";
    }

    public String score(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        List<Subject> subject = teacherMapper.subjectTeacher(userid);
        for (Subject subjects : subject) {
            subjects.setStudentcount(teacherMapper.studentCount(subjects.getSubjectid()));
        }
        model.addAttribute("subject", subject);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "teacher/scorePage";
    }

    public String scoreAll(HttpServletRequest request, Model model, Integer subjectid) {
        HttpSession session = request.getSession(true);
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        session.setAttribute("subjectid",subjectid);
        //课程所有学生的学号
        List<Subject> stu = teacherMapper.stuId(subjectid, userid);
        List<Student>student=new ArrayList<>();
        for (Subject stuId : stu) {
            student.add(teacherMapper.student(stuId.getStuid()));
        }
        for(Student students:student){
            students.setScore(teacherMapper.score(subjectid,students.getStuid()));
        }
        model.addAttribute("student",student);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "teacher/score";
    }
    public String revisePage(HttpServletRequest request,Model model,Integer stuid){
        HttpSession session = request.getSession(true);
        session.setAttribute("stuid",stuid);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "teacher/revisePage";
    }

    public String revise(HttpServletRequest request,Model model){
        HttpSession session = request.getSession(true);
        Integer userid = (Integer)session.getAttribute("userid");
        Integer subjectid= (Integer) session.getAttribute("subjectid");
        Integer stuid= (Integer) session.getAttribute("stuid");
        String subjectname=teacherMapper.subjectName(subjectid,userid);
        Integer score= Integer.valueOf(request.getParameter("score"));
        teacherMapper.scoreDel(subjectid,stuid);
        teacherMapper.scoreAdd(subjectid,subjectname,score,stuid);
        return "redirect:/teacher/score/+"+subjectid;
    }

    public String passwd(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        String password = request.getParameter("password");
        if(teacherMapper.passwd(userid,password)==1){
            model.addAttribute("name", request.getSession().getAttribute("name"));
            return "teacher/main";
        }else{
            model.addAttribute("result","修改失败");
            return "final";
        }
    }
    //查看个人信息
    public String material(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        model.addAttribute("teacher",teacherMapper.material(userid));
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "teacher/material";
    }
}
