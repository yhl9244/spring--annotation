package com.example.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {

    /**
     *
     * @param annotationMetadata 标注@Import注解的类的所有注解信息
     * @return 返回需要导入的组件全类名数组
     */
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        return new String[] {"com.example.entity.Blue", "com.example.entity.Yellow"};
    }
}
