package com.era;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    private final static int SEED = 42;
    private final static int SIZE = 1000;

    public static void main(String[] args){
        MyQueue<Integer> queue = new MyQueue<>();
        Random random = new Random(SEED);
        int sum = 0;

        for (int i = 0; i < SIZE; i++){
            int element = random.nextInt();
            queue.add(element);
            sum += element;
        }
        System.out.println("sum = " + sum);

        int sum2 = 0;
        while (true){
            Integer element = queue.poll();
            if (element == null) break;
            sum2 += element;
        }
        System.out.println("sum2 = " + sum2);

        List<Future<Integer>> results = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Random random2 = new Random(SEED);
        for (int i = 0; i < SIZE; i++){
            executorService.submit(()->{
                int element = random2.nextInt();
                queue.add(element);
            });
            Future<Integer> future = executorService.submit(queue::poll);
            results.add(future);
        }

        int sum3 = 0;
        for (Future<Integer> result : results){
            try {
                sum3 += result.get() == null ? 0 : result.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("sum3 = " + sum3);
    }
}
