package com.lin.service;

import com.lin.model.*;
import com.lin.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;


import java.util.List;

@Service
public class StudentService {
    @Autowired
    private UserMapper userMapper;
    //签到页面
    public String signPage(HttpServletRequest request, Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        List<Subject> subject = userMapper.subjectSign(userid);
        for (Subject subjects : subject) {
            if (userMapper.signReady(userid, subjects.getSubjectid()) != null) {
                subjects.setStatus(1);
            } else {
                subjects.setStatus(2);
            }
        }
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("subject", subject);
        return "student/sign";
    }

    //添加签到学生
    public String sign(HttpServletRequest request, Integer subjectid,Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        if(userMapper.signReady(userid,subjectid)==null){
            userMapper.sign(userid, subjectid);
            return "redirect:/student/sign";
        }else {
            model.addAttribute("result","请勿重复签到");
            return "final";
        }
    }

    //成绩查询
    public String score(HttpServletRequest request, Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        List<Score> score = userMapper.score(userid);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("score", score);
        return "student/score";
    }
    //课程查询
    public String subject(HttpServletRequest request, Model model) {
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        List<Subject> subject = userMapper.subject(userid);
        for(Subject subjects:subject){
            subjects.setTeachername(userMapper.teacherName(subjects.getTeacherid()));
        }
        model.addAttribute("name", request.getSession().getAttribute("name"));
        model.addAttribute("subject", subject);
        return "student/subject";
    }
    //修改密码
    public String passwd(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        String password = request.getParameter("password");
        if(userMapper.passwd(userid,password)==1){
            model.addAttribute("name", request.getSession().getAttribute("name"));
            return "student/main";
        }else{
            model.addAttribute("result","修改失败");
            return "final";
        }
    }
    //查看个人信息
    public String material(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        model.addAttribute("student",userMapper.material(userid));
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "student/material";
    }
}
