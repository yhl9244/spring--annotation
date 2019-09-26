package com.example.mvc;

import com.example.config.AppConfig;
import com.example.config.RootConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//web容器启动时候创建对象，调用方法初始化容器以前的前端控制器
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    // 获取根容器的配置类（spring的配置文件）父容器
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    // 获取web容器的配置类（springmvc的配置文件）子容器
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    // 获取DispatcherServlet的映射信息
    @Override
    protected String[] getServletMappings() {
        // "/"表示拦截所有请求（包括静态资源），但是不包括*.jsp
        // "/*"表示拦截所有请求（包括静态资源），包括*.jsp
        // jsp页面是tomcat的jsp引擎解析的
        return new String[]{"/"};
    }
}
