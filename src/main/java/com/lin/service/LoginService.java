package com.lin.service;

import com.lin.mapper.LoginMapper;
import com.lin.mapper.UserMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginMapper;

    //登陆验证 记录session
    public String login(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(true);
        Integer userid = Integer.valueOf(request.getParameter("userid"));
        String password = request.getParameter("password");
        String position = loginMapper.login(userid, password);
        if (position != null) {
            session.setAttribute("userid", userid);
            session.setAttribute("password", password);
            session.setAttribute("position", position);
            session.setAttribute("name",loginMapper.name(userid));
            switch (position) {
                case "student":
                    model.addAttribute("name",session.getAttribute("name"));
                    return "student/main";
                case "teacher":
                    model.addAttribute("name",session.getAttribute("name"));
                    return "teacher/main";
                case "center":
                    model.addAttribute("name",session.getAttribute("name"));
                    return "center/main";
                case "admin":
                    model.addAttribute("name",session.getAttribute("name"));
                    return "admin/main";
                default:
                    model.addAttribute("result", "未知权限");
                    return "login";
            }
        } else {
            model.addAttribute("result","用户名或者密码错误");
            return "login";
        }
    }
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        session.invalidate();
        return "redirect:/login";
    }
}
