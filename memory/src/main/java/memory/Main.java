package memory;

import java.lang.management.ManagementFactory;

public class Main {
    public static void main(String[] args){
        int size = 10_000_000;

        System.out.println("pid: " + ManagementFactory.getRuntimeMXBean().getName());

        while(true){
            System.gc();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Runtime runtime = Runtime.getRuntime();
            long memoryStart = runtime.totalMemory() - runtime.freeMemory();
            Object[] array = new Object[size];
            System.gc();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            long memoryStop = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Reference size: " + (memoryStop - memoryStart) / size);

            for (int i = 0; i < size; i++){
                //array[i] = new Object();
                //array[i] = "";
                //array[i] = new String(new char[0]);
                array[i] = new MyClass();
            }

          /*  System.gc();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            long memoryObject = runtime.totalMemory() - runtime.freeMemory();
            System.out.println("Object size: " + (memoryObject - memoryStop) / size);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class MyClass{
        private int i = 0;
        private long l = 1;
    }
}
