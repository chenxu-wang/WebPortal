package com.ccd.backend.framework.mvc;

import com.ccd.backend.framework.exception.MyException;
import com.ccd.backend.framework.jwt.JWTUtil;
import com.ccd.backend.entity.User;
import com.ccd.backend.framework.exception.MyException;
import com.ccd.backend.framework.jwt.JWTUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(JWTUtil.token);
        User user= JWTUtil.getUser(token);
        if(user==null){
            throw new MyException("Overtime or illegal token");
        }
        String newToken = JWTUtil.sign(user);
        response.setHeader(JWTUtil.token,newToken);
        request.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
