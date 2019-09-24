package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Data
@ToString
//@NoArgsConstructor
//@AllArgsConstructor
public class Person {

    /**
     * @Value用法：
     *      1.直接""赋值
     *      2.使用SPEL表达式：#{}
     *      3.使用${}读取配置文件
     */
    @Value("zs")
    private String name;
    @Value("#{20-3}")
    private Integer age;
    @Value("${person.nickName}")
    private String nickName;

    public Person() {}

    public Person(String name, Integer age) {
        //System.out.println("实例化Person对象");
        this.name = name;
        this.age = age;
    }
}
