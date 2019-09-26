package com.example.servlet;

import com.example.service.HelloService;

import javax.servlet.*;
import javax.servlet.annotation.HandlesTypes;
import java.util.EnumSet;
import java.util.Set;

// 容器启动时候会将@HandlesTypes指定类型下的子类（实现类，子接口等）传递过来
@HandlesTypes(value = {HelloService.class})
public class MyServletContainerInitializer implements ServletContainerInitializer {

    /**
     * 应用启动时候会运行onStartup方法
     * @param set
     * @param servletContext 代表当前web应用的servletContext，一个web应用对应一个servletContext
     * @throws ServletException
     *
     * 1）使用servletContext注册web组件（Servlet、Filter、Listener）
     * 2）必须在项目启动时候给ServletContext注册组件
     *      1.ServletContainerInitializer得到的ServletContext
     *      2.ServletContextListener中得到的ServletContext
     */
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("感兴趣的类型：");
        for (Class<?> aClass : set) {
            System.out.println(aClass);
        }
        // 注册Servlet组件
        ServletRegistration.Dynamic userServlet = servletContext.addServlet("userServlet", new UserServlet());
        // 配置Servlet的映射信息
        userServlet.addMapping("/user");
        // 注册Filter组件
        FilterRegistration.Dynamic userFilter = servletContext.addFilter("userFilter", new UserFilter());
        // 配置Filter的映射信息
        userFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
        // 注册Listener组件
        servletContext.addListener(UserListener.class);
    }
}
