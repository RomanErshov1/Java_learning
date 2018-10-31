package com.era;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue <T> {
    private Queue<T> queue = new LinkedList<T>();

    public void add(T e){
        queue.add(e);
    }

    public T poll(){
        return queue.poll();
    }
}
