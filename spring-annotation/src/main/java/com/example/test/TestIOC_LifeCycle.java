package com.example.test;

import com.example.config.AppConfigOfLifeCycle;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestIOC_LifeCycle {

    @Test
    public void test1() {
        // 容器初始化
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigOfLifeCycle.class);
        System.out.println("容器创建完成");
        ((AnnotationConfigApplicationContext) ac).close();
        System.out.println("容器关闭");
    }

    private void printBeans(ApplicationContext applicationContext) {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
