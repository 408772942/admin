package com.lin.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TeacherInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
        String position = (String) request.getSession().getAttribute("position");
        if (position == null) {
            response.sendRedirect(request.getContextPath() + "/nologin");
            return false;
        } else if (position.equals("teacher")) {
            return true;
        } else {
            response.sendRedirect(request.getContextPath() + "/404");
            return false;
        }
    }
}

