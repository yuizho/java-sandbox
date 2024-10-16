package io.github.yuizho.virtualthreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

public class VirtualThreadDemo {
    public static void main(String... args) {
        final int NTASKS = 100;
        try (ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();) {
            for (int i = 0; i < NTASKS; i++) {
                service.submit(() -> {
                    long id = Thread.currentThread().threadId();
                    // こちらを使うことでInterruptedExceptionをcatchしなくてもよくなる
                    LockSupport.parkNanos(1_000_000_000);
                    System.out.println(id);
                });
            }
        }
    }
}
