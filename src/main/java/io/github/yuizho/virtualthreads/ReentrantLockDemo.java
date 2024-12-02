package io.github.yuizho.virtualthreads;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    public static void main(String... args) {
        var TASKS = 20;
        var counter = new Counter();
        try (var service = Executors.newVirtualThreadPerTaskExecutor();) {
            for (int i = 0; i < TASKS; i++) {
                service.submit(counter::increment);
            }
        }
        System.out.println("count: " + counter.getCount());
    }
}

class Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
            System.out.println("increment: " + count + " on " + Thread.currentThread().threadId());
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }
}
