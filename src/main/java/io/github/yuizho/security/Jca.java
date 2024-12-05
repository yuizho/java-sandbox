package io.github.yuizho.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.TreeSet;

public class Jca {
    public static void main(String[] args) {
        System.out.println("---------------- list of algorithms ----------------");
        var algos = new TreeSet<String>();
        for (Provider provider : Security.getProviders()) {
            var services = provider.getServices();
            services.stream()
                    .map(s -> provider.getName() + ": " + s.getAlgorithm())
                    .forEach(algos::add);
        }
        algos.forEach(System.out::println);
    }
}
