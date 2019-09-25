package com.example.tx;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 声明式事务
 * 原理：
 *    1）@EnableTransactionManagement
 *          利用TransactionManagementConfigurationSelector给容器中导入组件
 *          导入两个组件：
 *              AutoProxyRegistrar
 *              ProxyTransactionManagementConfiguration
 *    2）AutoProxyRegistrar：给容器中注册InfrastructureAdvisorAutoProxyCreator组件
 *          InfrastructureAdvisorAutoProxyCreator：利用后置处理器机制在创建对象以后，包装对象返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用
 *    3）ProxyTransactionManagementConfiguration：给容器中注册事务增强器
 *          1）事务增强器要用事务注解信息：AnnotationTransactionAttributeSource解析事务注解
 *          2）事务拦截器：TransactionInterceptor保存事务属性信息，事务管理器。底层还是MethodInterceptor，目标方法执行时候执行拦截器链
 *              1.获取事务相关属性
 *              2.获取PlatformTransactionManager：如果没有添加指定任何TransactionManager，会从IOC容器中按照类型获取PlatformTransactionManager
 *          3）执行目标方法
 *              如果异常，获取事务管理器，利用事务管理器进行回滚操作；completeTransactionAfterThrowing
 *              如果正常，利用事务管理器提交事务；commitTransactionAfterReturning
 *
 */
//spring会对@Configuration进行处理。给容器中添加组件，多次调用都是从容器中找组件（如多次dataSource方法只会创建一次）
@Configuration
@ComponentScan("com.example.tx")
//开启基于注解的事务管理功能
@EnableTransactionManagement
public class TxConfig {

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    //注册事务管理器在容器中
    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
