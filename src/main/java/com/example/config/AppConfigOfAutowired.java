package com.example.config;

import com.example.dao.TestDao;
import com.example.entity.Car;
import com.example.entity.Color;
import com.example.entity.Person;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(value = {"com.example.controller","com.example.service", "com.example.dao", "com.example.entity"})
public class AppConfigOfAutowired {

    @Primary
    @Bean("testDao1")
    public TestDao testDao() {
        TestDao testDao = new TestDao();
        testDao.setLable("2");
        return testDao;
    }

    /**
     * @Bean标注的方法创建对象时，方法参数的值从容器中获取
     * @param car
     * @return
     */
    @Bean
    public Color color(Car car) {
        Color color = new Color();
        color.setCar(car);
        return color;
    }

    /**
     * 1.@Autowired自动注入：
     *      1）默认按照类型从容器中找对应组件：getBean(TestDao.class)
     *      2）如果找到多个相同类型的组件，再按照属性名称作为组件id去容器中查找：getBean("testDao")
     *      3）使用@Qualifier指定需要装配的组件id，而不使用属性名称：@Qualifier("testDao")
     *      4）自动装配默认一定要将属性赋值，没有会报错：@Autowired(required = false) 默认required为true
     *      5）@Primary默认使用首选的bean, 也可以使用@Qualifier指定需要装配的bean
     * 2.使用@Resource（JSR250）和@Inject（JSR330）[java规范注解]
     *      @Resource：
     *          和@Autowired一样实现自动装配功能，默认使用名称进行装配
     *          不支持@Primary、@Qualifier和@Autowired(required = false)功能
     *      @Inject：
     *          需要导入javax.inject包，和@Autowired功能一样，但是没有@Autowired(required = false)
     * 3.@Autowired：构造器、参数、方法、属性，都是从容器中获取参数组件的值
     *      1.[标注在方法参数] @Bean+参数方式，参数值从容器中获取，默认@Autowired可以省略不写
     *      2.[标注在构造器] 如果组件只有一个有参构造方法，此时构造器的@Autowired可以省略，组件还是可以注入进去
     *      3.标注在方法
     * 4.自定义组件使用spring容器底层的一些组件，实现XXXAware；使用XXXProcessor解析
     */
}
