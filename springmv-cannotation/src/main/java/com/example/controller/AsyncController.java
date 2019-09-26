package com.example.controller;

import com.example.mvc.MyDeferredResultQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;
import java.util.concurrent.Callable;

@Controller
public class AsyncController {


    /**
     * 1.控制器返回Callable
     * 2.spring异步处理，将Callable提交到TaskExecutor 使用一个隔离的线程进行执行
     * 3.DispatcherServlet和所有Filter退出web容器的线程，但是response保持打开状态
     * 4.Callable返回结果，SpringMVC将请求重新派发给容器，恢复之前的处理
     * 5.根据Callable返回结果。SpringMVC继续进行视图渲染流程等
     * 异步拦截器：
     *      1.原始API的AsyncListener
     *      2.SpringMVC实现AsyncHandlerInterceptor
     * @return
     */
    @RequestMapping("/async01")
    @ResponseBody
    public Callable<String> async01() {
        System.out.println("主线程开始。。" + Thread.currentThread() + "=>" + System.currentTimeMillis());
        Callable<String> callable = new Callable<String>() {

            public String call() throws Exception {
                System.out.println("副线程开始。。" + Thread.currentThread() + "=>" + System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("副线程结束。。" + Thread.currentThread() + "=>" + System.currentTimeMillis());
                return "Callable<String> async01() ";
            }
        };
        System.out.println("主线程结束。。" + Thread.currentThread() + "=>" + System.currentTimeMillis());
        return callable;
    }

    @RequestMapping("/create")
    @ResponseBody
    public String create() {
        String order = UUID.randomUUID().toString();
        DeferredResult<Object> deferredResult = MyDeferredResultQueue.get();
        deferredResult.setResult(order);
        return "success========>" + order;
    }

    @RequestMapping("/createOrder")
    @ResponseBody
    public DeferredResult<Object> createOrder() {
        DeferredResult<Object> deferredResult = new DeferredResult<Object>(3000L, "createOrder fail");
        MyDeferredResultQueue.save(deferredResult);
        return deferredResult;
    }
}
