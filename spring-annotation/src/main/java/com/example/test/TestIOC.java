package com.example.test;

import com.example.config.AppConfig;
import com.example.entity.ColorFactoryBean;
import com.example.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

public class TestIOC {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    public void test1() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
//        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName);
//        }
    }

    @Test
    public void test2() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("IOC容器加载完成。。。");
        System.out.println(ac.getBean("person2"));
        System.out.println(ac.getBean("person2"));
        System.out.println(ac.getBean("person2") == ac.getBean("person2"));
    }

    @Test
    public void test3() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println("IOC容器加载完成。。。");
        ac.getBean("person3");
        ac.getBean("person3");
    }

    @Test
    public void test4() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        Environment environment = ac.getEnvironment();
        System.out.println(environment.getProperty("os.name"));
        String[] names = ac.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }
        Map<String, Person> beans = ac.getBeansOfType(Person.class);
        System.out.println(beans);
    }

    @Test
    public void test5() {
        printBeans(ac);
    }

    @Test
    public void test6() {
        printBeans(ac);
        System.out.println(ac.getBean("colorFactoryBean"));
        System.out.println(ac.getBean("colorFactoryBean"));
        System.out.println(ac.getBean(ColorFactoryBean.class));
        System.out.println(ac.getBean("&colorFactoryBean"));
    }

    private void printBeans(ApplicationContext applicationContext) {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
