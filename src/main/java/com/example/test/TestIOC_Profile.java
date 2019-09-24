package com.example.test;

import com.example.config.AppConfigOfProfile;
import com.example.entity.Yellow;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

public class TestIOC_Profile {

    // 1.使用命令行参数:虚拟机参数 -Dspring.profiles.active=test
    // 2.使用AnnotationConfigApplicationContext无参构造器设置环境 代码激活

    @Test
    public void test1() {
        // 容器初始化
        // ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigOfProfile.class);
        // 创建ApplicationContext
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        // 设置需要激活的环境
        ac.getEnvironment().setActiveProfiles("test");
        // 注册主配置类
        ac.register(AppConfigOfProfile.class);
        // 启动刷新容器
        ac.refresh();
        printBeans(ac);
        System.out.println(ac.getBean(Yellow.class));
    }

    private void printBeans(ApplicationContext applicationContext) {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
