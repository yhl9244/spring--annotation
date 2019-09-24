package com.example.entity;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ToString
public class Boss {

    private Car car;

    //@Autowired
    // 方法参数从IOC容器中获取
    // 只有一个无参构造器可以省略@Autowired
    public Boss(@Autowired Car car) {
        this.car = car;
        System.out.println("boss 有参构造器");
    }

    public Car getCar() {
        return car;
    }

    //@Autowired
    // 标注在方法上，spring会创建当前对象时调用方法完成赋值
    // 方法参数从IOC容器中获取
    public void setCar(Car car) {
        this.car = car;
    }
}
