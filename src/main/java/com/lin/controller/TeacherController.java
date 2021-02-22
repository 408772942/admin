package com.lin.controller;


import com.lin.service.StudentService;
import com.lin.service.TeacherService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;


@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    //查看所有课程
    @RequestMapping(value = "/teacher/sign")
    public String sign(HttpServletRequest request, Model model){
        return  teacherService.signPage(request,model);
    }
    //开启签到
    @RequestMapping(value = "/teacher/signOpen/{subjectid}")
    public String signOpen(HttpServletRequest request, @PathVariable Integer subjectid,Model model) {
        return teacherService.signOpen(request,subjectid,model);
    }
    //关闭签到
    @RequestMapping(value = "/teacher/signOff/{subjectid}")
    public String signOff(HttpServletRequest request, @PathVariable Integer subjectid) {
        return teacherService.signOff(request,subjectid);
    }
    //查看所有关闭课程
    @RequestMapping(value = "/teacher/signOpenAll")
    public String signOpenAll(HttpServletRequest request, Model model){
        return teacherService.signOpenAll(request,model);
    }
    //查看所有开启课程
    @RequestMapping(value = "/teacher/signOffAll")
    public String signOffAll(HttpServletRequest request, Model model){
        return teacherService.signOffAll(request,model);
    }
    //查看课程
    @RequestMapping(value = "/teacher/subject")
    public String subject(HttpServletRequest request, Model model) {
        return  teacherService.subject(request,model);
    }
    //查看签到人数
    @RequestMapping(value = "/teacher/signAll/{subjectid}")
    public String signAll(HttpServletRequest request,Model model,@PathVariable Integer subjectid){
        return teacherService.signAll(request,model,subjectid);
    }
    //选择班级
    @RequestMapping(value = "/teacher/subjectAdd")
    public String add(HttpServletRequest httpServletRequest,Model model){
        return teacherService.add(httpServletRequest,model);
    }
    //班级学生
    @RequestMapping(value = "/teacher/subjectAdd/student")
    public String student(HttpServletRequest request,Model model){
        return teacherService.studentShow(request,model);
    }
    //添加全部学生
    @RequestMapping(value = "/teacher/studentAllAdd")
    public String studentAllAdd(HttpServletRequest request,Model model){
        return teacherService.studentAllAdd(request,model);
    }
    //课程添加学生
    @RequestMapping(value = "/teacher/subjectAdd/{subjectid}/{stuid}")
    public String studentAdd(HttpServletRequest request,@PathVariable Integer subjectid,@PathVariable Integer stuid,Model model){
        return teacherService.studentAdd(request,subjectid,stuid,model);
    }
    //课程删除学生
    @RequestMapping(value = "/teacher/subjectDel/{stuid}")
    public String studentDel(HttpServletRequest request,@PathVariable Integer stuid,Model model){
        return teacherService.studentDel(request,stuid,model);
    }
    //删除全部学生
    @RequestMapping(value = "/teacher/studentAllDel")
    public String studentAllDel(HttpServletRequest request,Model model){
        return teacherService.studentAllDel(request,model);
    }
    //查看该门课程所有学生
    @RequestMapping(value = "/teacher/studentAll/{subjectid}")
    public String studentAll(HttpServletRequest request,Model model,@PathVariable Integer subjectid){
        return teacherService.studentAll(request,model,subjectid);
    }
    //查询首页
    @RequestMapping(value = "/teacher/select")
    public String select(HttpServletRequest request,Model model){
        return teacherService.select(request, model);
    }
    //查看学生
    @RequestMapping(value = "/teacher/select",method = RequestMethod.POST)
    public String selectStudent(HttpServletRequest request,Model model){
        return teacherService.studentSelect(request,model);
    }
    //成绩首页
    @RequestMapping(value = "/teacher/score")
    public String score(HttpServletRequest request,Model model){
        return teacherService.score(request,model);
    }
    //查看该门课程所有学生成绩
    @RequestMapping(value = "/teacher/score/{subjectid}")
    public String scoreAll(HttpServletRequest request,Model model,@PathVariable Integer subjectid){
        return teacherService.scoreAll(request,model,subjectid);
    }
    //修改首页
    @RequestMapping(value = "/teacher/revise/{stuid}")
    public String revisePage(HttpServletRequest request,Model model,@PathVariable Integer stuid){
        return teacherService.revisePage(request,model,stuid);
    }
    //修改学生成绩
    @RequestMapping(value = "/teacher/revise",method = RequestMethod.POST)
    public String revise(HttpServletRequest request,Model model){
        return teacherService.revise(request,model);
    }
    //修改密码
    @RequestMapping(value = "/teacher/passwd",method = RequestMethod.GET)
    public String password(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "teacher/passwd";
    }
    @RequestMapping(value = "/teacher/passwd",method = RequestMethod.POST)
    public String passwd(HttpServletRequest request,Model model){
        return teacherService.passwd(request,model);
    }
    //查看个人信息
    @RequestMapping(value = "/teacher/material")
    public String material(HttpServletRequest request,Model model){
        return teacherService.material(request,model);
    }
}
