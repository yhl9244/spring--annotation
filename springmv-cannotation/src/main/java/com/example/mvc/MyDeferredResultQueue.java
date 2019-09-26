package com.example.mvc;

import org.springframework.web.context.request.async.DeferredResult;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MyDeferredResultQueue {

    private static Queue<DeferredResult<Object>> queue = new ConcurrentLinkedDeque<DeferredResult<Object>>();

    public static void save(DeferredResult<Object> deferredResult) {
        queue.add(deferredResult);
    }

    public static DeferredResult<Object> get() {
        return queue.poll();
    }
}
