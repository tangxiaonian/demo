package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class MyThread_1{

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = this.lock.readLock();
    private Lock writeLock = this.lock.writeLock();

    private volatile Map<String, String> map = new HashMap<>(16);

    public String get(String key) {
        readLock.lock();
        try {
            return map.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            readLock.unlock();
        }
        return null;
    }

    public void put(String key, String value) {
        writeLock.lock();
        try {
            map.put(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            writeLock.unlock();
        }
    }

}

public class TestMain1 {

    public static void main(String[] args) throws InterruptedException {

        MyThread_1 myThread_1 = new MyThread_1();

        for (int i = 0; i < 10; i++) {
            final int f = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    myThread_1.put(f + "", f + "");
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            final int f = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("取出的数据为:"+myThread_1.get(f + ""));;
                }
            }).start();
        }

    }
}
