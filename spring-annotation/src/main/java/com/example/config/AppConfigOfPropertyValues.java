package com.example.config;

import com.example.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
// 读取外部配置文件的k/v属性保存到运行时环境变量中
@PropertySource(value = {"classpath:person.properties"})
public class AppConfigOfPropertyValues {

    @Bean
    public Person person() {
        return new Person();
    }
}
