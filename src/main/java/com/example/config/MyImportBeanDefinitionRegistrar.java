package com.example.config;

import com.example.entity.RainRow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param annotationMetadata 标注@Import注解的类的所有注解信息
     * @param beanDefinitionRegistry beanDefinition的注册类
     *      把需要添加到容器中的bean调用BeanDefinitionRegistry.registerBeanDefinition手动注册到容器中
     */
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean red = beanDefinitionRegistry.containsBeanDefinition("com.example.entity.Red");
        boolean blue = beanDefinitionRegistry.containsBeanDefinition("com.example.entity.Blue");
        if (red && blue) {
            beanDefinitionRegistry.registerBeanDefinition("rainRow", new RootBeanDefinition(RainRow.class));
        }
    }
}
