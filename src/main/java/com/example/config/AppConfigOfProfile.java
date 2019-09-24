package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.entity.Yellow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;

/**
 * Profile
 *      spring可以根据当前的环境动态切换和激活一系列组件的功能
 * @Profile 指定在哪个环境可以注册到容器中，不指定任何环境都可以注册组件
 *      1.加@Profile的bean，只有被激活的环境才能注册到容器，默认环境为default
 *      2.加@Profile的类，只有被激活的环境整个类的所有配置才能够生效
 *      3.没有加@Profile，任何环境都能够注册到容器
 */
@Profile("test")
@Configuration
@PropertySource("classpath:db.properties")
public class AppConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.name}")
    private String user;

    private StringValueResolver stringValueResolver;
    private String driverClassName;

    @Bean
    public Yellow yellow() {
        return new Yellow();
    }

    @Bean
    @Profile("test")
    public DataSource dataSourceTest(@Value("${db.password}") String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(stringValueResolver.resolveStringValue("${db.driver}"));
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Profile("dev")
    public DataSource dataSourceDev(@Value("${db.password}") String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl("jdbc:mysql://localhost:3306/world");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Profile("prod")
    public DataSource dataSourceProd(@Value("${db.password}") String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/microservice");
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
        driverClassName = stringValueResolver.resolveStringValue("${db.driver}");
    }

}
