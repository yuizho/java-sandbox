package io.github.yuizho.virtualthreads;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class PinningDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // https://dev.java/learn/new-features/virtual-threads/#pinning

        // ./mvnw clean compile && java \
        // -XX:StartFlightRecording=dumponexit=true,filename=./virtualthraed_8thread_pinned.jfr \
        //    -Djdk.virtualThreadScheduler.parallelism=1                \
        //    -Djdk.virtualThreadScheduler.maxPoolSize=1                \
        //    -Djdk.tracePinnedThreads=full                             \
        //    -cp target/classes                                        \
        //    io.github.yuizho.virtualthreads.PinningDemo

        ExecutorService service =
                Executors.newVirtualThreadPerTaskExecutor();
        // Executors.newCachedThreadPool();

        final int TASKS = 20;
        long start = System.nanoTime();
        for (int i = 1; i <= TASKS; i++) {
            service.submit(() -> block());
            // service.submit(() -> rblock());
        }
        for (int i = 1; i <= TASKS; i++) {
            service.submit(() -> noblock());
        }
        service.close();
        long end = System.nanoTime();
        System.out.printf("%.2f%n", (end - start) * 1E-9);
    }

    public static synchronized void block() {
        System.out.println("Entering block " + Thread.currentThread());
        LockSupport.parkNanos(1_000_000_000);
        System.out.println("Exiting block " + Thread.currentThread());
    }
    private static Lock lock = new ReentrantLock();
    public static void rblock() {
        lock.lock();
        try {
            System.out.println("Entering rblock " + Thread.currentThread());
            LockSupport.parkNanos(1_000_000_000);
            System.out.println("Exiting rblock " + Thread.currentThread());
        } finally {
            lock.unlock();
        }
    }
    public static void noblock() {
        System.out.println("Entering noblock " + Thread.currentThread());
        LockSupport.parkNanos(1_000_000_000);
        System.out.println("Exiting noblock " + Thread.currentThread());
    }
}
