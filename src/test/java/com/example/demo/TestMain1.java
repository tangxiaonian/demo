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

    private Map<String, String> map = new HashMap<>(16);

    public void get(String key) {
        readLock.lock();
        try {
            map.get(key);
        } catch (Exception e) {

        }
    }

    public void put(String key, String value) {

    }


}

public class TestMain1 {

    public static void main(String[] args) throws InterruptedException {

    }
}
