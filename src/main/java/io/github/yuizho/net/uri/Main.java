package io.github.yuizho.net.uri;

import java.net.URI;

public class Main {
    public static void main(String... args) {
        var uri1 = URI.create("http://example.com/path/").resolve("aaaa");
        System.out.println(uri1); // http://example.com/path/aaaa

        var uri2 = URI.create("http://example.com/path").resolve("aaaa");
        System.out.println(uri2); // unexpected join: http://example.com/aaaa

        var uri3 = URI.create("http://example.com/path").resolve("/aaaa");
        System.out.println(uri3); // unexpected join: http://example.com/aaaa

        var uri4 = URI.create("http://example.com/path").resolve("/aaaa");
        System.out.println(uri4); // unexpected join: http://example.com/aaaa
    }
}
