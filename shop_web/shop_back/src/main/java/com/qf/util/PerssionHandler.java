package com.qf.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * linzebin
 * 时间2019/7/4 19:01
 */
@Component //让spring扫描
public class PerssionHandler {
    /*进行权限校验*/
    public boolean hasperssion(HttpServletRequest request, Authentication authentication){
        //用于处理url和存入的url的关系 因为url后可能携带参数user/insert/1
        AntPathMatcher antPathMatcher=new AntPathMatcher();
        //判断是否通过登录验证
        //这个方法可以获得通过登录验证的security得Userdetails对象
        Object principal = authentication.getPrincipal();
        //判断该对象是否实现了userdetails接口 如果实现了 说明通过身份验证
        if (principal instanceof UserDetails){
            //获得前端的url
            String requestURI = request.getRequestURI();
            //进行权限判断 
            UserDetails user= (UserDetails) principal;
            //获得该对象下的方法，返回一个权限集合
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            System.out.println("拥有的权限集合"+authorities.toString());
            for (GrantedAuthority authority : authorities) {
                System.out.println("请求的url"+requestURI+"对象的url"+authority.getAuthority());
                if (antPathMatcher.match(requestURI,authority.getAuthority())){
                    System.out.println("拥有该权限");
                    return true;
                }
            }
        }
        return false;

    }
}
