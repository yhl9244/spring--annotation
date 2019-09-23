package com.example.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition {

    /**
     *
     * @param conditionContext 判断条件可以使用的上下文环境
     * @param annotatedTypeMetadata 注释信息
     * @return
     */
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        // 获取ioc容器的beanfactory
        ConfigurableListableBeanFactory beanFactory = conditionContext.getBeanFactory();
        // 获取类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        // 获取环境信息
        Environment environment = conditionContext.getEnvironment();
        // 获取bean定义的注册类
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        // 获取资源加载器
        ResourceLoader resourceLoader = conditionContext.getResourceLoader();
        if (environment.getProperty("os.name").contains("linux")) {
            return true;
        }
        return false;
    }
}
