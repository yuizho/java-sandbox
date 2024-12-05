package io.github.yuizho.virtualthreads;

import java.util.*;
import java.util.concurrent.*;
import java.net.*;
import java.net.http.*;

public class CapturingTaskResults {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newVirtualThreadPerTaskExecutor();
        List<Callable<String>> callables = new ArrayList<>();
        final int ADJECTIVES = 4;
        for (int i = 1; i <= ADJECTIVES; i++)
            callables.add(() -> get("https://horstmann.com/random/adjective"));
        callables.add(() -> get("https://horstmann.com/random/noun"));
        List<String> results = new ArrayList<>();
        for (Future<String> f : service.invokeAll(callables))
            results.add(f.get());
        System.out.println(String.join(" ", results));
        service.close();
    }

    private static HttpClient client = HttpClient.newHttpClient();

    // 仮想スレッドの同時実行数を2つに制限するセマフォ
    private static final Semaphore SEMAPHORE = new Semaphore(2);

    public static String get(String url) {
        try {
            var request = HttpRequest.newBuilder().uri(new URI(url)).GET().build();
            SEMAPHORE.acquire();
            System.out.println("Requesting " + url + "　by " + Thread.currentThread().threadId());
            try {
                Thread.sleep(1000);
                return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            } finally {
                SEMAPHORE.release();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            var rex = new RuntimeException();
            rex.initCause(ex);
            throw rex;
        }
    }
}
