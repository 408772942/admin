package com.lin.controller;

import com.lin.service.LoginService;
import com.lin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView test(HttpServletRequest request){
        if(request.getSession().getAttribute("position")!=null){
            return new ModelAndView("login_again.html");
        }else {
            return new ModelAndView("login.html");
        }
    }
    //验证登陆
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model){
        return loginService.login(request,model);
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        return loginService.logout(request);
    }
}
