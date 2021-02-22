package com.lin.controller;

import com.lin.service.CenterService;
import com.lin.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.AccountException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
public class CenterController {
    @Autowired
    CenterService centerService;
    //进入添加课程
    @RequestMapping(value = "/center/subject")
    public String subject(HttpServletRequest request,Model model){
        return  centerService.subject(request,model);
    }
    @RequestMapping(value = "/center/subject",method = RequestMethod.POST)
    public String subjectAdd(HttpServletRequest request,Model model){
        return centerService.subjectAdd(request,model);
    }
    //删除课程
    @RequestMapping(value = "/center/subjectDel/{subjectid}")
    public String subjectDel(@PathVariable Integer subjectid,Model model){
        return centerService.subjectDel(model,subjectid);
    }
    //查看所有课程
    @RequestMapping(value = "/center/subjectAll")
    public String subjectAll(Model model,HttpServletRequest request){
        return centerService.subjectAll(model,request);
    }
    //查看课程学生
    @RequestMapping(value = "/center/studentSubAll/{subjectid}")
    public String studentSubAll(HttpServletRequest request,Model model,@PathVariable Integer subjectid){
        return centerService.studentSubAll(request,model,subjectid);
    }
    //选择班级
    @RequestMapping(value = "/center/studentSubAdd")
    public String add(HttpServletRequest httpServletRequest,Model model){
        return centerService.add(httpServletRequest,model);
    }
    //班级学生
    @RequestMapping(value = "/center/subjectSubAdd/student")
    public String student(HttpServletRequest request,Model model){
        return centerService.studentShow(request,model);
    }
    //删除学生
    @RequestMapping(value = "/center/subjectSubDel/{stuid}")
    public String studentSubDel(HttpServletRequest request,@PathVariable Integer stuid,Model model){
        return centerService.studentSubDel(request,stuid,model);
    }
    //添加学生
    @RequestMapping(value = "/center/subjectAdd/{subjectid}/{stuid}")
    public String studentAdd(HttpServletRequest request,@PathVariable Integer subjectid,@PathVariable Integer stuid,Model model){
        return centerService.studentSubAdd(request,subjectid,stuid,model);
    }
    //添加全部学生
    @RequestMapping(value = "/center/studentAllAdd")
    public String studentAllAdd(HttpServletRequest request,Model model){
        return centerService.studentAllAdd(request,model);
    }
    //班级
    @RequestMapping(value = "/center/studentAll")
    public String studentAll(HttpServletRequest httpServletRequest,Model model){
        return centerService.studentAll(httpServletRequest,model);
    }
    //班级后学生
    @RequestMapping(value = "/center/studentAll/{classname}")
    public String studentAllByClass(HttpServletRequest request,Model model,@PathVariable String classname) throws UnsupportedEncodingException {
        return centerService.studentAllByClass(request, model,classname);
    }
    //删除学生
    @RequestMapping(value = "/center/studentDelByClass/{stuid}")
    public String studentDelByClass(HttpServletRequest request,Model model,@PathVariable Integer stuid) throws UnsupportedEncodingException {
        return centerService.studentDelByClass(request,model,stuid);
    }
    //添加学生
    @RequestMapping(value = "/center/studentAddByClass")
    public String studentAddByClass(HttpServletRequest request,Model model){
        return centerService.studentAddByClass(request,model);
    }
    @RequestMapping(value = "/center/studentAddByClass",method = RequestMethod.POST)
    public String studentAddByClass1(HttpServletRequest request,Model model) throws UnsupportedEncodingException {
        return centerService.studentAddByClass1(request,model);
    }
    //删除班级
    @RequestMapping(value = "/center/classDel/{classname}")
    public String classDel (HttpServletRequest request,Model model,@PathVariable String classname){
        return centerService.classDel(request,model,classname);
    }
    //添加班级
    @RequestMapping(value = "/center/classAdd")
    public String classPage(HttpServletRequest request,Model model){
        return centerService.classPage(request,model);
    }
    @RequestMapping(value = "/center/classAdd",method = RequestMethod.POST)
    public String classAdd(HttpServletRequest request,Model model){
        return centerService.classAdd(request,model);
    }
    @RequestMapping(value = "/center/select")
    public String selectPage(HttpServletRequest request,Model model){
        return centerService.selectPage(request,model);
    }
    @RequestMapping(value = "/center/select",method = RequestMethod.POST)
    public String select(HttpServletRequest request,Model model){
        return centerService.select(request,model);
    }

    //查看所有教师
    @RequestMapping(value = "/center/teacherAll")
    public String teacherAll(HttpServletRequest request,Model model){
        return centerService.teacherAll(request,model);
    }

    @RequestMapping(value = "/center/material")
    public String material(HttpServletRequest request,Model model){
        return centerService.material(request,model);
    }

    //修改密码
    @RequestMapping(value = "/center/passwd",method = RequestMethod.GET)
    public String password(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "center/passwd";
    }
    @RequestMapping(value = "/center/passwd",method = RequestMethod.POST)
    public String passwd(HttpServletRequest request,Model model){
        return centerService.passwd(request,model);
    }

}
