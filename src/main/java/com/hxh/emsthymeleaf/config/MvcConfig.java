package com.hxh.emsthymeleaf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    // 通过这里配置：不需要为每一个访问thymeleaf模板页面单独开发一个controller请求了
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // ViewController：请求路径      ViewName：跳转视图
        registry.addViewController("login").setViewName("login");

        registry.addViewController("register").setViewName("register");

        registry.addViewController("emplist").setViewName("emplist");

        registry.addViewController("addEmp").setViewName("addEmp");

        registry.addViewController("updateEmp").setViewName("updateEmp");
    }
}
