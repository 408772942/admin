package com.lin.service;

import com.lin.mapper.AdminMapper;
import com.lin.mapper.CenterMapper;
import com.lin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    public String centerShow(HttpServletRequest request,Model model){
        List<User> user=adminMapper.show();
        model.addAttribute("user",user);
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "admin/show";
    }

    public String add(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        Integer userid= Integer.valueOf(request.getParameter("userid"));
        String password=request.getParameter("password");
        String position=request.getParameter("position");
        String name=request.getParameter("name");
        String sex=request.getParameter("sex");
        String birthday=request.getParameter("birthday");
        String temp=request.getParameter("phone");
        Integer phone=Integer.valueOf(request.getParameter("phone"));
        String addr=request.getParameter("addr");
        System.out.println(sex);
        if(adminMapper.find(userid)==null){
            adminMapper.userAdd(userid,password,position,name);
            adminMapper.add(userid,name,sex,birthday,phone,addr);
            return "redirect:/admin/show";
        }else {
            model.addAttribute("result","已存在");
            return "final";
        }
    }
    public String del(Integer userid,Model model,HttpServletRequest request){
        if(adminMapper.del(userid)==1&&adminMapper.teacherDel(userid)==1){
            return "redirect:/admin/show";
        }else{
            model.addAttribute("result","删除失败");
            return "final";
        }
    }
    public String passwd(HttpServletRequest request,Model model){
        Integer userid = (Integer) request.getSession().getAttribute("userid");
        String password = request.getParameter("password");
        if(adminMapper.passwd(userid,password)==1){
            model.addAttribute("name", request.getSession().getAttribute("name"));
            return "admin/main";
        }else{
            model.addAttribute("result","修改失败");
            return "final";
        }
    }
    public String detailed(HttpServletRequest request,Model model,Integer userid){
        model.addAttribute("teacher",adminMapper.teacher(userid));
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "admin/material";
    }
}