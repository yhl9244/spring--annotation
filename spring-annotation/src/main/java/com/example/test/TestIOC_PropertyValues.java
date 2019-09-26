package com.example.test;

import com.example.config.AppConfigOfLifeCycle;
import com.example.config.AppConfigOfPropertyValues;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

public class TestIOC_PropertyValues {

    @Test
    public void test1() {
        // 容器初始化
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigOfPropertyValues.class);
        printBeans(ac);
        System.out.println(ac.getBean("person"));
        Environment environment = ac.getEnvironment();
        System.out.println(environment.getProperty("person.nickName"));
    }

    private void printBeans(ApplicationContext applicationContext) {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
