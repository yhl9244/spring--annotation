package com.example.entity;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog {

    public Dog() {
        System.out.println("dog constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("dog init");
    }

    @PreDestroy
    public void destory() {
        System.out.println("dog destory");
    }
}
