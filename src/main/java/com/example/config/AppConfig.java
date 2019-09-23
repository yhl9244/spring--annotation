package com.example.config;

import com.example.condition.LinuxCondition;
import com.example.condition.WindowCondition;
import com.example.entity.Color;
import com.example.entity.ColorFactoryBean;
import com.example.entity.Person;
import com.example.entity.Red;
import com.example.service.TestService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

@Configuration
//@Conditional(LinuxCondition.class) // 过滤条件
@ComponentScan("com.example")
//@ComponentScan(value = "com.example", excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})})
// 使用includeFilters 必须将useDefaultFilters设置为false
//@ComponentScan(value = "com.example", includeFilters = {
//            @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
//            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {TestService.class}),
//            @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
//        }, useDefaultFilters = false)
// 导入组件 id默认为全类名
@Import({Color.class, Red.class,MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class AppConfig {

    // 注册一个bean，id默认为方法名称
    @Bean(value = "person")
    public Person person1() {
        return new Person("zs", 25);
    }


    /**
     * singleton：单实例（默认值）IOC容器启动时会创建实例放入容器中，以后从容器中获取
     * prototype：多实例 IOC容器创建完成，每次getBean时都会创建实例。
     * web环境：request：同一个请求创建一个实例
     *         session： 同一个session创建一个实例
     * @return
     */
    @Scope(value = "prototype")
    @Bean(value = "person2")
    public Person person2() {
        return new Person("ls", 21);
    }

    /**
     * 懒加载：
     *      单实例bean：容器启动时创建对象
     *      懒加载：容器启动时不创建对象，第一次getBean时创建对象（只创建一次）适用于单实例bean
     * @return
     */
    @Lazy
    @Bean(value = "person3")
    public Person person3() {
        System.out.println("容器中加入bean");
        return new Person("ww", 22);
    }

    /**
     * @Conditional 按照一定的条件进行判断，满足条件才会注册bean
     * 方法上：当前方法有效
     * 类上：整个类有效
     */
    //@Conditional(WindowCondition.class)
    @Bean("bill")
    public Person person4() {
        return new Person("Bill Gates", 60);
    }

    //@Conditional(LinuxCondition.class)
    @Bean("linux")
    public Person person5() {
        return new Person("Linux", 60);
    }

    /**
     * @Import
     *      1.@Import：容器自动注入组件，id默认为全类名
     *      2.ImportSelector：返回需要导入的组件全类名数组
     *      3.ImportBeanDefinitionRegistrar 手动注册bean到容器中
     */

    /**
     * 注册bean到IOC容器中：
     *      1.包扫描+组件标注注解（@Controller@Service@Repository@Component）
     *      2.@Bean（导入第三方包里组件,如：DataSource等）
     *      3.@Import（给容器中导入一个组件）
     *      4.使用FactoryBean（工厂Bean）
     *          1）默认获取是工厂bean调用getObject获取到的对象
     *          2）获取工厂bean本身需要再前面+&或者使用工厂bean.class
     */
    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }
}
