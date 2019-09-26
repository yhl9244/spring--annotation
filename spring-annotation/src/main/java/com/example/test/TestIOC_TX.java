package com.example.test;

import com.example.aop.MathCalculator;
import com.example.config.AppConfigOfAOP;
import com.example.tx.TxConfig;
import com.example.tx.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestIOC_TX {

    // 1.使用命令行参数:虚拟机参数 -Dspring.profiles.active=test
    // 2.使用AnnotationConfigApplicationContext无参构造器设置环境 代码激活

    @Test
    public void test1() {
        // 容器初始化
        ApplicationContext ac = new AnnotationConfigApplicationContext(TxConfig.class);
        ac.getBean(UserService.class).insertUser();
        ((AnnotationConfigApplicationContext) ac).close();
    }
}
