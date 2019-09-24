package com.example.test;

import com.example.config.AppConfigOfAutowired;
import com.example.config.AppConfigOfPropertyValues;
import com.example.dao.TestDao;
import com.example.entity.Boss;
import com.example.entity.Car;
import com.example.entity.Color;
import com.example.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

public class TestIOC_Autowired {

    @Test
    public void test1() {
        // 容器初始化
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfigOfAutowired.class);
        //TestService bean = ac.getBean(TestService.class);
        //bean.print();
        //System.out.println(ac.getBean(TestDao.class));
        System.out.println(ac.getBean(Boss.class));
        System.out.println(ac.getBean(Car.class));
        System.out.println(ac.getBean(Color.class));
    }

    private void printBeans(ApplicationContext applicationContext) {
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
