package com.example.test;

import com.example.ext.ExtConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestIOC_EXT {

    // 1.使用命令行参数:虚拟机参数 -Dspring.profiles.active=test
    // 2.使用AnnotationConfigApplicationContext无参构造器设置环境 代码激活

    @Test
    public void test1() {
        // 容器初始化
        ApplicationContext ac = new AnnotationConfigApplicationContext(ExtConfig.class);
        // 发布事件
        ac.publishEvent(new ApplicationEvent(new String("发布事件")) {
        });
        ((AnnotationConfigApplicationContext) ac).close();
    }
}
