package com.example.servlet;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/async", asyncSupported = true)
public class HelloAsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.支持异步处理asyncSupported = true
        // 2.开启异步模式
        System.out.println("主线程开始。。" + Thread.currentThread() + "=>" + System.currentTimeMillis());
        AsyncContext asyncContext = req.startAsync();
        // 3.开始异步处理业务逻辑
        asyncContext.start(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("副线程开始。。" + Thread.currentThread() + "=>" + System.currentTimeMillis());
                    sayHello();
                    asyncContext.complete();
                    //AsyncContext asyncContext1 = req.getAsyncContext();
                    // 4.获取响应
                    ServletResponse response = asyncContext.getResponse();
                    response.getWriter().write("hello...async");
                    System.out.println("副线程结束。。" + Thread.currentThread() + "=>" + System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("主线程结束。。" + Thread.currentThread() + "=>" + System.currentTimeMillis());
    }

    private void sayHello() throws InterruptedException {
        System.out.println(Thread.currentThread() + "process...");
        Thread.sleep(3000);
    }
}
