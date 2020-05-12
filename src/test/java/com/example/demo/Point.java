package com.example.demo;

import java.util.concurrent.locks.StampedLock;

public class Point {

    private final StampedLock stampedLock = new StampedLock();

    private double x;
    private double y;

    public void move(double deltaX, double deltaY) {
        // 加写锁
        long stamp = stampedLock.writeLock();
        try{
            System.out.println( "写入值：" + deltaX );
            this.x += deltaX;
            this.y += deltaY;
        }finally {
            // 释放写锁
            stampedLock.unlockWrite(stamp);
        }
    }
    public double distanceFromOrigin() {
        long stamp = stampedLock.tryOptimisticRead();
        double currentX = x;
        double currentY = y;
        // 验证版本号 true 没有其他线程进行修改
        if (!stampedLock.validate(stamp)) {
            System.out.println( "重新获取锁..." );
            // 获取一个悲观锁
            stamp = stampedLock.readLock();
            try {
                // 重新获取值
                currentX = x;
                currentY = y;
            }finally {
                stampedLock.unlockRead(stamp);
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }


    public static void main(String[] args) {

        Point point = new Point();

        for (int i = 0; i < 10; i++) {

            final double f = i * 1.0f;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    point.move(f,f);
                }
            }).start();
        }

        for (int i = 0; i < 10; i++) {
            final int f = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("计算得到的值:--->"+point.distanceFromOrigin());
                }
            }).start();
        }

    }
}