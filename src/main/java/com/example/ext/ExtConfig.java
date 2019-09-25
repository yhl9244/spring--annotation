package com.example.ext;

import com.example.entity.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 扩展组件：
 *     BeanPostProcessor：bean后置处理器，bean创建对象初始化前后拦截
 *     BeanFactoryPostProcessor：beanFactory后置处理器，
 *       beanFactory标准初始化之后使用；所有的bean定义已经保存加载到beanFactory，但是bean的实例还未创建
 *       1）IOC容器创建对象
 *       2）invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessors
 *          从BeanFactory中找到所有类型为BeanFactoryPostProcessor的组件并执行他们的方法，再初始化创建其他组件之前执行
 *     BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *       所有bean定义将要被加载，但是bean实例还未创建，优先于BeanFactoryPostProcessor执行
 *       可以利用BeanDefinitionRegistryPostProcessor给容器中额外添加一些组件
 *       1）IOC容器创建对象
 *       2）invokeBeanFactoryPostProcessors(beanFactory);
 *          从BeanFactory中找到所有类型为BeanDefinitionRegistryPostProcessor的组件
 *          先执行invokeBeanDefinitionRegistryPostProcessors->postProcessBeanDefinitionRegistry方法
 *          再执行invokeBeanFactoryPostProcessors->postProcessBeanFactory方法
 *       3）从BeanFactory中找到所有类型为BeanFactoryPostProcessor的组件并执行他们的方法
 *     ApplicationListener：监听容器中发布事件，完成事件驱动
 *        public interface ApplicationListener<E extends ApplicationEvent> extends EventListener
 *        监听ApplicationEvent及其下面子事件
 *        方式：1.实现ApplicationListener接口来监听事件（ApplicationEvent及其子类）
 *             2.@EventListener
 *               原理：使用EventListenerMethodProcessor来解析方法上的@EventListener
 *               SmartInitializingSingleton：
 *                  1）IOC容器创建对象并刷新容器：refresh()
 *                  2）finishBeanFactoryInitialization(beanFactory);
 *                      1.创建所有的单实例bean：getBean(beanName)
 *                      2.获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型，如果是调用afterSingletonsInstantiated();
 *
 *        原理：ContextRefreshedEvent、TestIOC_EXT$1、ContextClosedEvent
 *            1.ContextRefreshedEvent事件：
 *              1）容器创建对象：refresh()
 *              2）容器刷新完成：finishRefresh()会发布ContextRefreshedEvent事件
 *            2.自己发布事件TestIOC_EXT$1
 *            3.ContextClosedEvent事件：容器关闭
 *            【事件发布流程】publishEvent(new ContextRefreshedEvent(this));
 *                1.获取事件派发器（多播器）getApplicationEventMulticaster()
 *                2.派发事件multicastEvent
 *                3.获取到所有的getApplicationListeners：
 *                    for (final ApplicationListener<?> listener : getApplicationListeners(event, type))
 *                    1）如果有Executor，可以支持使用Executor进行异步派发 Executor executor = getTaskExecutor();
 *                    2）否则同步方式直接执行listener方法：invokeListener(listener, event);
 *                      拿到listener回调执行onApplicationEvent方法：listener.onApplicationEvent(event);
 *            【事件派发器】:
 *                1.容器创建对象：refresh()
 *                2.initApplicationEventMulticaster:初始化ApplicationEventMulticaster
 *                  先去容器找是否有id=applicationEventMulticaster的组件
 *                  如果有直接getBean获取，
 *                  如果没有this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 *                  并且加入到容器中，可以在其他组件派发事件时自动注入applicationEventMulticaster。
 *            【容器中有哪些监听器】
 *                1.容器创建对象：refresh()
 *                2.registerListeners():
 *                  从容器中拿到所有的监听器
 *                  String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 *                  注册到applicationEventMulticaster中
 *                  getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
 */
@Configuration
@ComponentScan("com.example.ext")
public class ExtConfig {

    @Bean
    public Car car() {
        return new Car();
    }
}
