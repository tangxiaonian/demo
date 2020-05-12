package com.example.demo;

import java.util.concurrent.TimeUnit;

class MyThread extends Thread{

    private volatile boolean stop = false;

    @Override
    public void run() {
        while (!stop) {
            System.out.println( Thread.currentThread().getName() + "is running!" );
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                stop = true;
            }
            System.out.println( Thread.currentThread().getName() + "is exit!" );
        }
    }

}

public class TestMain {

    public static void main(String[] args) throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.start();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("myThread 开始中断....");
        myThread.interrupt();
        System.out.println("myThread 中断结束....");
    }
}
