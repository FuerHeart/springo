package com.zh.springo.config;


public class DeadLockDemo {
    public static final Object resource = new Object();
    public static final Object resource1 = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            synchronized (resource){
                System.out.println(Thread.currentThread() + "get resource");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread() + "waiting get resource1");
                synchronized (resource1){
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        },"Thread One").start();
        new Thread(()->{
            synchronized (resource1){
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread() + "waiting get resource");
                synchronized (resource){
                    System.out.println(Thread.currentThread() + "get resource");
                }
            }
        },"Thread Two").start();
    }
}
