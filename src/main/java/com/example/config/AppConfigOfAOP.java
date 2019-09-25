package com.example.config;

import com.example.aop.LogAspect;
import com.example.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 1.将业务类和切面类都加入IOC容器，并指定切面类@Aspect
 * 2.再切面类上面加通知注解：@Before @After @AfterReturning @AfterThrowing @Around
 * 3.开启基于注解的AOP @EnableAspectJAutoProxy
 *
 * AOP原理：[给容器中注册组件，工作原理]
 *      1.@EnableAspectJAutoProxy->@Import({AspectJAutoProxyRegistrar.class})
 *          利用AspectJAutoProxyRegistrar自定义给容器中注册bean->org.springframework.aop.config.internalAutoProxyCreator=AnnotationAwareAspectJAutoProxyCreator
 *      2.AnnotationAwareAspectJAutoProxyCreator:
 *          AnnotationAwareAspectJAutoProxyCreator
 *              ->AspectJAwareAdvisorAutoProxyCreator
 *                  ->AbstractAdvisorAutoProxyCreator
 *                      ->AbstractAutoProxyCreator: implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                      关注后置处理器：bean初始化前后、自动装配BeanFactory
 *        AbstractAutoProxyCreator.setBeanFactory()
 *        AbstractAutoProxyCreator.postProcessBeforeInstantiation()
 *        AbstractAutoProxyCreator.postProcessAfterInitialization()
 *
 *        AbstractAdvisorAutoProxyCreator.setBeanFactory()
 *        AbstractAdvisorAutoProxyCreator.initBeanFactory()
 *
 *        AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *      流程：1.传入配置类，创建IOC容器
 *           2.注册配置类，调用refresh()刷新容器
 *           3.注册bean后置处理器，拦截bean的创建：registerBeanPostProcessors(beanFactory);
 *              1）获取IOC容器中已经定义需要创建对象的所有BeanPostProcessor
 *              2）给容器添加其他的BeanPostProcessor
 *              3）优先注册实现了PriorityOrdered接口的BeanPostProcessor
 *              4）再注册实现了Ordered接口的BeanPostProcessor
 *              5）最后注册未实现优先级接口的其他普通BeanPostProcessor
 *              6）注册BeanPostProcessor，如果获取不到创建BeanPostProcessor对象保存到容器中
 *                  创建internalAutoProxyCreator【AnnotationAwareAspectJAutoProxyCreator】
 *                      1.创建Bean实例createBeanInstance
 *                      2.populateBean:给bean属性赋值
 *                      3.initializeBean:初始化bean
 *                          1.invokeAwareMethods():处理Aware接口的方法回调
 *                          2.applyBeanPostProcessorsBeforeInitialization():执行后置处理器的postProcessBeforeInitialization
 *                          3.invokeInitMethods():执行初始化方法
 *                          4.applyBeanPostProcessorsAfterInitialization():执行后置处理器的postProcessAfterInitialization
 *                      4.BeanPostProcessor(AnnotationAwareAspectJAutoProxyCreator)创建成功：->aspectJAdvisorFactory、aspectJAdvisorsBuilder
 *              7）把BeanPostProcessor注册到BeanFactory中：beanFactory.addBeanPostProcessor(postProcessor);
 *           ===========创建和注册AnnotationAwareAspectJAutoProxyCreator过程=================
 *           4.finishBeanFactoryInitialization(beanFactory);完成BeanFactory的初始化，注册剩下的单实例bean
 *              1）遍历获取容器中所有的bean，依次创建对象 getBean->doGetBean->getSingleton
 *              2）创建bean：
 *                  【AnnotationAwareAspectJAutoProxyCreator会在所有bean创建之前进行拦截->InstantiationAwareBeanPostProcessor->postProcessBeforeInstantiation】
 *                  1.先从缓存中获取当前bean是否被创建，如果获取到直接使用，否则再创建；只要创建好的bean都会被缓存起来
 *                  2.createBean：AnnotationAwareAspectJAutoProxyCreator会在任何bean创建之前先尝试返回bean实例
 *                      【BeanPostProcessor】是在bean对象创建完成初始化前后调用的
 *                      【InstantiationAwareBeanPostProcessor】是在创建bean实例之前先尝试用后置处理器返回对象的
 *                      1.resolveBeforeInstantiation():解析BeforeInstantiation 希望后置处理器在此能返回一个代理对象
 *                          1）后置处理器尝试返回对象：
 *                              bean = applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);
 *                              拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor，执行postProcessBeforeInstantiation
 *                              if (bean != null) {
 * 						            bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
 *                              }
 *                      2.doCreateBean():真正创建一个bean实例，和3.6一致
 *                  【AnnotationAwareAspectJAutoProxyCreator会在所有bean创建之前进行拦截->InstantiationAwareBeanPostProcessor->postProcessBeforeInstantiation】
 *                  1.postProcessBeforeInstantiation：
 *                      1）判断当前bean是否在advisedBeans，保存了所有需要增强的bean
 *                      2）判断当前bean是否是基础类型：Advice、Pointcut、Advisor、AopInfrastructureBean 或者是否是切面：@Aspect
 *                      3）是否需要跳过：
 *                          1.获取候选的增强器（切面里面通知方法）List<Advisor> candidateAdvisors
 *                            每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor
 *                            判断每一个增强器是否是AspectJPointcutAdvisor，如果是返回true
 *                          2.永远返回false
 *                  2.postProcessAfterInitialization：
 *                      return wrapIfNecessary(bean, beanName, cacheKey); //包装对象
 *                      1）获取当前bean的所有增强器（通知方法）Object[] specificInterceptors
 *                          找到候选的增强器（也就是找哪些通知方法是需要切入当前bean方法的）
 *                          获取到能在当前bean使用的增强器
 *                          给增强器排序
 *                      2）保存当前bean到advisedBeans中
 *                      3）如果当前bean需要增强，创建当前bean代理对象：createProxy
 *                          获取所有增强器（通知方法）->保存到ProxyFactory
 *                          创建代理对象：Spring自己决定，可以设置proxy_target_class为true强制使用cglib
 *                              JdkDynamicAopProxy：jdk动态代理 实现接口
 *                              ObjenesisCglibAopProxy：cglib动态代理 未实现接口并且未设置proxy_target_class为true
 *                      4）给容器中返回当前组件使用cglib增强后的代理对象
 *                      5）以后容器中获取到就是代理对象，执行目标方法时代理对象就会执行通知方法
 *                  3.目标方法执行
 *                      容器中保存了组件的代理对象（cglib增强后的代理对象），对象里面保存了详细信息（如增强器，目标对象等）
 *                      1.CglibAopProxy.intercept();拦截目标方法执行
 *                      2.根据ProxyFactory获取将要执行的目标拦截器链：
 *                          List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
 *                              1.创建List<Object> interceptorList：长度为1个默认的ExposeInvocationInterceptor和定义的增强器（通知方法）加起来的数量
 *                              2.遍历所有的增强器将其封装为Interceptor：registry.getInterceptors(advisor);
 *                              3.将增强器转为List<MethodInterceptor>
 *                                  如果是MethodInterceptor直接加入集合中，
 *                                  如果不是则使用AdvisorAdapter将增强器转成MethodInterceptor，
 *                                  转化完成返回MethodInterceptor数组
 *                      3.如果没有拦截器链直接执行目标方法
 *                      4.如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链等信息传入创建一个CglibMethodInvocation
 *                        并调用Object retVal = CglibMethodInvocation.proceed();
 *                        拦截器链（每个通知方法被包装成方法拦截器，利用MethodInterceptor机制）
 *                      5.拦截器链触发过程：
 *                          1.如果没有拦截器直接执行目标方法，或者拦截器索引和拦截器数组大小-1一样，（也就是执行到最后一个拦截器）直接执行目标方法
 *                          2.链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器都是等待下一个拦截器执行完成返回后再执行。拦截器链的机制保证通知方法与目标方法执行顺序
 *    总结：
 *      1）@EnableAspectJAutoProxy开启AOP功能
 *      2）@EnableAspectJAutoProxy给容器注册组件AnnotationAwareAspectJAutoProxyCreator
 *      3）AnnotationAwareAspectJAutoProxyCreator一个后置处理器
 *      4）容器创建流程：
 *          1.registerBeanPostProcessors(beanFactory);注册后置处理器，创建AnnotationAwareAspectJAutoProxyCreator
 *          2.finishBeanFactoryInitialization()初始化剩下的单实例bean
 *              1）创建业务逻辑组件和切面组件
 *              2）AnnotationAwareAspectJAutoProxyCreator会拦截组件的创建过程，判断组件是否需要增强，如果是会将切面的通知方法包装成增强器（Advisor）
 *              3）组件创建完成wrapIfNecessary，如果是，给业务逻辑组件创建一个代理对象
 *      5）执行目标方法：代理对象执行目标方法->CglibAopProxy.intercept();拦截目标方法执行
 *          1）得到目标方法的拦截器链（增强器转成MethodInterceptor）
 *          2）利用拦截器链式机制，依次进入每一个拦截器进行执行
 *          3）效果：
 *              正常执行：前置通知->目标方法->后置通知->返回通知
 *              出现异常：前置通知->目标方法->后置通知->异常通知
 */
@Configuration
// 开启基于注解的AOP功能
@EnableAspectJAutoProxy
public class AppConfigOfAOP {

    @Bean
    public MathCalculator mathCalculator() {
        return new MathCalculator();
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }
}

