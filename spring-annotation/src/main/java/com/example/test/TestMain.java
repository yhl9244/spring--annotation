package com.example.test;

import com.example.config.AppConfig;
import com.example.entity.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestMain {

    public static void main(String[] args) {
        //ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:spirng-config.xml");
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        Person person = (Person) ac.getBean("person");
        System.out.println(person);
        String[] names = ac.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }
    }
}
