package com.lin.service;

import com.lin.mapper.CenterMapper;
import com.lin.mapper.TeacherMapper;
import com.lin.model.Student;
import com.lin.model.Subject;
import com.lin.model.Teacher;
import com.lin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Service
public class CenterService {
    @Autowired
    private CenterMapper centerMapper;
    @Autowired
    private TeacherMapper teacherMapper;

    public String subject(HttpServletRequest request,Model model){
        List<Teacher>teacher=centerMapper.teacherAll();
        List<User>user=centerMapper.teacher();
        List<Teacher>teachers=new ArrayList<>();
        for(Teacher teacherall:teacher){
            for(User user1:user){
                if(user1.getUserid().equals(teacherall.getTeacherid())){
                    teachers.add(teacherall);
                }
            }
        }
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("teacher",teachers);
        return "center/subjectPage";
    }

    public String subjectAdd(HttpServletRequest request,Model model) {
        Integer subjectid = Integer.valueOf(request.getParameter("subjectid"));
        String subjectname = request.getParameter("classname");
        Integer teacherid = Integer.valueOf(request.getParameter("teacherid"));
        if (centerMapper.subjectSelect(subjectid) == null) {
            if (centerMapper.subjectAdd(subjectid, subjectname, teacherid) == 1) {
                return "redirect:/center/subjectAll";
            } else
                model.addAttribute("result","添加失败");
                return "final";
        } else {
            model.addAttribute("result","已存在");
            return "final";
        }
    }

    public String subjectDel(Model model,Integer subjectid) {
        if (centerMapper.subjectDel(subjectid)== 1) {
            centerMapper.signDel(subjectid);
            return "redirect:/center/subjectAll";
        } else {
            model.addAttribute("result","删除失败");
            return "final";
        }
    }

    public String subjectAll(Model model,HttpServletRequest request) {
        List<Subject> subjectAll = centerMapper.subjectAll();
        for(Subject subject:subjectAll){
            subject.setTeachername(centerMapper.teacherName(subject.getTeacherid()));
            subject.setStudentcount(centerMapper.count(subject.getSubjectid()));
        }
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("subject", subjectAll);
        return "center/subject";
    }
    public String studentSubDel(HttpServletRequest request, Integer stuid, Model model) {
        HttpSession session = request.getSession(true);
        Integer subjectid= (Integer) session.getAttribute("subjectid");
        if (centerMapper.studentSubDel(subjectid,stuid) == 1) {
            return "redirect:/center/studentSubAll/"+subjectid;
        }
        model.addAttribute("result","删除失败");
        return "final";
    }

    public String studentSubAll(HttpServletRequest request, Model model, Integer subjectid) {
        HttpSession session = request.getSession(true);
        session.setAttribute("subjectid",subjectid);
        List<Subject> student= centerMapper.studentSubAll(subjectid);
        List<Student> studentAll = new ArrayList<>();
        for (Subject stuId : student) {
            studentAll.add(teacherMapper.studentAll(stuId.getStuid()));
        }
        for (Student students:studentAll){
            students.setSubjectid(subjectid);
        }
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("student", studentAll);
        return "center/studentDel";
    }
    public String add(HttpServletRequest request,Model model){
        List<String>classname=teacherMapper.classname();
        model.addAttribute("classname",classname);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "center/class";
    }
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
        return "center/studentAdd";
    }
    public String studentSubAdd(HttpServletRequest request, Integer subjectid, Integer stuid,Model model) {
        String subjectName =centerMapper.subjectName(subjectid);
        Integer subjectState = centerMapper.subjectState(subjectid);
        if (teacherMapper.studentAddReady(stuid, subjectid) == null){
            Integer userid=centerMapper.teacherid(subjectid);
            if (teacherMapper.subjectAdd(subjectid, subjectName, stuid, userid, subjectState) == 1) {
                return "redirect:/center/studentSubAll/"+subjectid;
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
        HttpSession session = request.getSession(true);
        Integer userid=centerMapper.teacherid((Integer) session.getAttribute("subjectid"));
        Integer subjectid= (Integer) session.getAttribute("subjectid");
        String classname= (String) session.getAttribute("class");
        List<Student>studentShow=teacherMapper.studentShow(classname,subjectid);
        String subjectName = teacherMapper.subjectName(subjectid, userid);
        Integer subjectState = teacherMapper.subjectState(subjectid, userid);
        for (Student student:studentShow){
            student.setSubjectid(subjectid);
            teacherMapper.subjectAdd(subjectid, subjectName, student.getStuid(), userid, subjectState);
        }
        return "redirect:/center/studentSubAll/"+subjectid;
    }

    public String studentAllByClass(HttpServletRequest request,Model model,String classname) throws UnsupportedEncodingException {
        String Classname = URLDecoder.decode(classname, "utf-8");
        model.addAttribute("student",centerMapper.studentAllByClass(Classname));
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "center/studentClassAdd";
    }

    public String studentAll(HttpServletRequest request,Model model){
        List<Student>classAll=centerMapper.classAll();
        for(Student name:classAll){
            name.setStudentcount(centerMapper.classcount(name.getClassname()));
        }
        model.addAttribute("classname",classAll);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "center/classAll";
    }

    public String studentDelByClass(HttpServletRequest request,Model model,Integer stuid) throws UnsupportedEncodingException {
        String Classname=centerMapper.classname(stuid);
        centerMapper.studentDelByClass(stuid);
        centerMapper.signDelByClass(stuid);
        centerMapper.userDelByClass(stuid);
        String classname = URLEncoder.encode(Classname, "utf-8");
        return "redirect:/center/studentAll/"+classname;
    }

    public String studentAddByClass(HttpServletRequest request,Model model){
        List<Student>classAll=centerMapper.classAll();
        for(Student name:classAll){
            name.setStudentcount(centerMapper.classcount(name.getClassname()));
        }
        model.addAttribute("classname",classAll);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "center/studentByClass";
    }

    public String studentAddByClass1(HttpServletRequest request,Model model) throws UnsupportedEncodingException {
        Integer userid= Integer.valueOf(request.getParameter("userid"));
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        String Classname=request.getParameter("classname");
        Integer num= Integer.valueOf(request.getParameter("num"));
        String sex=request.getParameter("sex");
        String birthday=request.getParameter("birthday");
        Integer phone= Integer.valueOf(request.getParameter("phone"));
        String addr=request.getParameter("addr");
        if(centerMapper.studentAdd(userid,name,Classname,num,sex,birthday,phone,addr)==1&&centerMapper.userAddByClass(userid,password,name)==1){
            String classname = URLEncoder.encode(Classname, "utf-8");
            return "redirect:/center/studentAll/"+classname;
        }else {
            model.addAttribute("result","添加失败");
            return "final";
        }
    }
    public String classDel(HttpServletRequest request,Model model,String classname){
        List<Student>students=centerMapper.studentAllByClass(classname);
        for(Student student:students){
            centerMapper.userDelByClass(student.getStuid());
        }
        centerMapper.classDel(classname);
        return "redirect:/center/studentAll";
    }
    public String classPage(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "center/classAdd";
    }
    public String classAdd(HttpServletRequest request,Model model){
        String classname=request.getParameter("classname");
        if(centerMapper.classAdd(classname)==1){
            return "redirect:/center/studentAll";
        }else {
            model.addAttribute("result","添加失败");
            return "final";
        }
    }
    public String selectPage(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "center/selectPage";
    }
    public String select(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        String teacherid= request.getParameter("teacherid");
        String teachername= request.getParameter("teachername");
        String classname=request.getParameter("classname");
        String studentid=request.getParameter("stuid");
        String studentname=request.getParameter("studentname");
        if(!teacherid.isEmpty()){
            Teacher teacher=centerMapper.teacherSelect(Integer.valueOf(teacherid));
            model.addAttribute("teacher",teacher);
            return "center/teacher";
        }else if(!teachername.isEmpty()){
            Teacher teacher=centerMapper.teacherSelectByName(teachername);
            model.addAttribute("teacher",teacher);
            return "center/teacher";
        }else if(!classname.isEmpty()){
            List<Student>students=teacherMapper.selectByClass(classname);
            model.addAttribute("student",students);
            return "center/student";
        }else  if(!studentid.isEmpty()){
            Student student=teacherMapper.selectById(Integer.valueOf(studentid));
            model.addAttribute("student",student);
            return "center/student";
        }else if(!studentname.isEmpty()){
            Student student=teacherMapper.selectByName(studentname);
            model.addAttribute("student",student);
            return "center/student";
        }
        model.addAttribute("result","输入错误");
        return "final";
    }

    public String teacherAll(HttpServletRequest request,Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        model.addAttribute("teacher", centerMapper.teacherBy(userid));
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "center/teacherAll";
    }

    public String material(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("teacher",centerMapper.self(userid));
        return "center/material";
    }

    public String passwd(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        String password = request.getParameter("password");
        if(teacherMapper.passwd(userid,password)==1){
            model.addAttribute("name", request.getSession().getAttribute("name"));
            return "center/main";
        }else{
            model.addAttribute("result","修改失败");
            return "final";
        }
    }

}
