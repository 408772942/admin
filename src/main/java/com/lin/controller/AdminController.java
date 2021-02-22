package com.lin.controller;

import com.lin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/admin/show")
    public String student(HttpServletRequest request,Model model){
        return adminService.centerShow(request,model);
    }

    //添加
    @RequestMapping(value = "/admin/add")
    public String addPage(){
        return "admin/add";
    }
    @RequestMapping(value = "/admin/add",method = RequestMethod.POST)
    public String add(HttpServletRequest request,Model model){
        return adminService.add(request,model);
    }
    //删除
    @RequestMapping(value = "/admin/del/{userid}")
    public String studentDel(@PathVariable Integer userid,Model model,HttpServletRequest request){
        return adminService.del(userid,model,request);
    }
    //修改密码
    @RequestMapping(value = "/admin/passwd",method = RequestMethod.GET)
    public String password(HttpServletRequest request,Model model){
        model.addAttribute("name", request.getSession().getAttribute("name"));
        return "admin/passwd";
    }
    @RequestMapping(value = "/admin/passwd",method = RequestMethod.POST)
    public String passwd(HttpServletRequest request,Model model){
        return adminService.passwd(request,model);
    }
    @RequestMapping(value = "/admin/detailed/{userid}")
    public String detailed(HttpServletRequest request,Model model, @PathVariable Integer userid){
        return adminService.detailed(request,model,userid);
    }

}
