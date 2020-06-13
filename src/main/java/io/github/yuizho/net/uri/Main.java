package io.github.yuizho.net.uri;

import java.net.URI;

public class Main {
    public static void main(String... args) {
        URI uri1 = URI.create("http://example.com/").resolve("aaaa");
        System.out.println(uri1);
    }
}
