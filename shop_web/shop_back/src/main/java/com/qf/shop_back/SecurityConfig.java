package com.qf.shop_back;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.Service.IBackService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * linzebin
 * 时间2019/7/4 17:21
 */
/*配置放行頁面，放行登陸，注銷，靜態頁面，其他全部攔截，關閉防止攻擊*/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Reference
    IBackService backService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
     http
             .formLogin()
                 .loginPage("/tologin")
                 .loginProcessingUrl("/login")
                 .failureUrl("/tologin?error=1")
                 .permitAll()
             .and()
             .logout().permitAll()
             .and()
             .authorizeRequests()
             //静态资源全部放行
                    .mvcMatchers("/resources/**").permitAll()
                    //只要身份认证了 就可以通过
                    .mvcMatchers("/").authenticated()
                         //进行权限校验
                   // .anyRequest().access("@perssionHandler.hasperssion(request,authentication)")
                      .anyRequest().authenticated()
             .and()
             .csrf().disable()
             .headers().frameOptions().sameOrigin()
             .and()
             /*处理iframe请求，让security放行（必加）*/
             .exceptionHandling().accessDeniedPage("/noperssion");
    }

    //自定义认证过程

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(backService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
}
