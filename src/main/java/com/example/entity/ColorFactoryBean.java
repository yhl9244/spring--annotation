package com.example.entity;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactoryBean implements FactoryBean<Color> {

    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean-----");
        return new Color();
    }

    public Class<?> getObjectType() {
        return Color.class;
    }

    // 默认是创建单例bean，可以重写isSingleton改变
//    public boolean isSingleton() {
//        return false;
//    }
}
