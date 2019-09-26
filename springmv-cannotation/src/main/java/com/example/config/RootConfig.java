package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

// Spring不扫描controller：父容器
@ComponentScan(value = "com.example", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Controller.class)
})
public class RootConfig {
}
