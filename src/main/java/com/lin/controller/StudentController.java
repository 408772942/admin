package com.lin.controller;



import com.lin.model.Score;
import com.lin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class StudentController {
    @Autowired
    private StudentService studentService;


    //查看签到科目
    @RequestMapping(value = "/student/sign")
    public String signPage(HttpServletRequest request, Model model){
        return studentService.signPage(request,model);
    }
    //进行签到
    @RequestMapping(value = "/student/sign/{subjectid}")
    public String sign(HttpServletRequest request, @PathVariable Integer subjectid,Model model) {
        return  studentService.sign(request, subjectid,model);
    }
    //查看成绩
    @RequestMapping(value = "/student/score")
    public String  score(HttpServletRequest request,Model model){
        return studentService.score(request,model);
    }
    //查看课程
    @RequestMapping(value = "/student/subject")
    public String subject(HttpServletRequest request,Model model){
        return studentService.subject(request,model);
    }
    //修改密码
    @RequestMapping(value = "/student/passwd",method = RequestMethod.GET)
    public String password(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "student/passwd";
    }
    @RequestMapping(value = "/student/passwd",method = RequestMethod.POST)
    public String passwd(HttpServletRequest request,Model model){
        return studentService.passwd(request,model);
    }
    //查看个人信息
    @RequestMapping(value = "/student/material")
    public String material(HttpServletRequest request,Model model){
        return studentService.material(request,model);
    }


}
