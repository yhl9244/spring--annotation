package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
//@NoArgsConstructor
//@AllArgsConstructor
public class Person {

    private String name;
    private Integer age;

    public Person() {}

    public Person(String name, Integer age) {
        //System.out.println("实例化Person对象");
        this.name = name;
        this.age = age;
    }
}
