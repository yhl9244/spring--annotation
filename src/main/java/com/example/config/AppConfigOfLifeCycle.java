package com.example.config;

import com.example.entity.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * bean生命周期
 *      bean创建---初始化--销毁
 * bean创建
 *      单实例： 加载IOC容器创建
 *      多实例： 每次调用getBean创建
 * bean初始化
 *      实例创建完成并赋值好开始调用初始化方法
 * bean销毁
 *      单实例bean容器关闭时销毁
 *      多实例bean容器不会管理，不会调用销毁方法
 * 初始化和销毁方式
 *      1.指定初始化和销毁方法 @Bean指定initMethod和destroyMethod
 *      2.实现接口InitializingBean和DisposableBean
 *      3.通过JSR250注解@PostConstruct和@PreDestroy
 *      4.通过BeanPostProcessor bean后置处理器：在bean初始化前后进行一些处理工作
 *              postProcessBeforeInitialization：初始化之前
 *              postProcessAfterInitialization：初始化之后
 * spring底层对BeanPostProcessor的使用：
 *      bean赋值、注入其他组件、@Autowired、生命周期注解功能、@Async等。
 */
@Configuration
@ComponentScan("com.example.entity")
@Import(MyBeanPostProcessor.class)
public class AppConfigOfLifeCycle {

    @Bean(initMethod = "init", destroyMethod = "destory")
    public Car car() {
        return new Car();
    }
}
