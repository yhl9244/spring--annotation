package com.example.config;

import com.example.mvc.MyInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.*;

// SpringMVC只扫描Controller：子容器
// useDefaultFilters = false 禁用默认过滤规则
@ComponentScan(value = "com.example", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)
}, useDefaultFilters = false)
@EnableWebMvc
public class AppConfig extends WebMvcConfigurerAdapter {

    // 视图解析器
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        // 默认所有的页面从/WEB-IBF/xxx.jsp
        //registry.jsp();
        registry.jsp("/WEB-INF/views/", ".jsp");

    }

    // 静态资源访问
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // 拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }
}
