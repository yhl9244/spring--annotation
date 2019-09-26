package com.example.service;

import com.example.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

@Service
public class TestService {

    // 自动装配默认一定要将属性赋值，没有会报错。
    //@Autowired(required = false)
    // 使用@Qualifier指定装配的组件，不加默认使用属性名称去找对应组件
    //@Qualifier("testDao")
    //@Resource(name = "testDao1")
    @Inject
    private TestDao testDao;

    public void print() {
        System.out.println(testDao);
    }

}
