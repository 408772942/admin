package com.lin.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
        if (request.getSession().getAttribute("position") != null) {
            response.sendRedirect("/404");
            return false;
        }else {
            response.sendRedirect("/login");
            return false;
        }
    }
}


