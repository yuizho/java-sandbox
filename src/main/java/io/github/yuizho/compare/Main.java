package io.github.yuizho.compare;

import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String... args) {
        // sort with Comparable interface
        List<ComparableBean> comparableBeans = List.of(
                new ComparableBean(3, 2),
                new ComparableBean(2, 5),
                new ComparableBean(2, 4),
                new ComparableBean(1, 1)
        );
        comparableBeans
                .stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("----------------------");

        // sort with Comparator
        var comparator =
                Comparator.comparingInt((SimpleBean simpleBean) -> simpleBean.key1)
                .thenComparing(simpleBean -> simpleBean.key2);
        List<SimpleBean> simpleBeans = List.of(
                new SimpleBean(3, 2),
                new SimpleBean(2, 5),
                new SimpleBean(2, 4),
                new SimpleBean(1, 1)
        );
        simpleBeans
                .stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }

    static class SimpleBean {
        final int key1;
        final int key2;
        SimpleBean(int key1, int key2) {
            this.key1 = key1;
            this.key2 = key2;
        }

        @Override
        public String toString() {
            return "SimpleBean{" +
                    "key1=" + key1 +
                    ", key2=" + key2 +
                    '}';
        }
    }

    static class ComparableBean implements Comparable<ComparableBean> {
        final int key1;
        final int key2;
        ComparableBean(int key1, int key2) {
            this.key1 = key1;
            this.key2 = key2;
        }

        @Override
        public int compareTo(ComparableBean comparableBean) {
            int result = Integer.compare(key1, comparableBean.key1);
            if (result == 0) {
                result = Integer.compare(key2, comparableBean.key2);
            }
            return result;
        }

        @Override
        public String toString() {
            return "ComparableBean{" +
                    "key1=" + key1 +
                    ", key2=" + key2 +
                    '}';
        }
    }
}
