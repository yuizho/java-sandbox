package io.github.yuizho.thread.concurrent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;

public class CompletionServiceSample {
    public static void main(String... args) {
        int concurrency = 5;
        try (ExecutorService executor = Executors.newFixedThreadPool(concurrency)) {
            CompletionService<String> completionService =
                    new ExecutorCompletionService<>(executor);
            for (int i = 0; i < concurrency; i++) {
                final int index = i;
                completionService.submit(() -> {
                    Thread.sleep(1000 * index);
                    return Thread.currentThread().threadId() + " " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                });
            }

            for (int i = 0; i < concurrency; i++) {
                try {
                    Future<String> future = completionService.take();
                    System.out.println(future.get());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
