package com.era;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue <T> {
    private Queue<T> queue = new LinkedList<>();

    private Object monitor = new Object();

    public void add(T e){
        synchronized (monitor){
            queue.add(e);
            monitor.notifyAll();
        }
    }

    public T poll(){
        synchronized (monitor) {
            while (queue.size() < 1){
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return queue.poll();
    }
}
