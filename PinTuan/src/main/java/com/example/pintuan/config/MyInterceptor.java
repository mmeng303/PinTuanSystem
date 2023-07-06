package com.example.pintuan.config;


import com.example.pintuan.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path=request.getRequestURI();
        System.out.println("path="+path);
        if(handler instanceof HandlerMethod){
            // 判断token是否为空
            String token=request.getHeader("token");
            System.out.println("token="+token);
            if(StringUtils.isEmpty(token)){
                System.out.println("token为空！");
                return false;
            }else{
                // 如果token不为空
                Claims claims = JwtUtils.validateJWT(token).getClaims();
                if(claims==null){
                    System.out.println("鉴权失败");
                    return false;
                }else{
                    System.out.println("鉴权成功");
                    return true;
                }
            }
        }else{
            return true;
        }
    }
}
