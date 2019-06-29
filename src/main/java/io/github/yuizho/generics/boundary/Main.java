package io.github.yuizho.generics.boundary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EmptyStackException;
import java.util.List;

public class Main {
    public static void main(String... args) {
        Stack<Number> stack = new Stack<>();
        stack.pushAll(List.of(1, 2, 3, 4, 5));
        System.out.println(stack);
        // ちなみにここList<Number>でも通る
        List<Object> popedItems = new ArrayList<>();
        stack.popAll(popedItems);
        System.out.println(stack);
        System.out.println(popedItems);
    }

    /**
     * PECS(Producer-Extends, Consumer-Super)のサンプルコード
     */
    public static class Stack<E> {
        private List<E> elements;

        public Stack() {
            elements = new ArrayList<>();
        }

        public void push(E elm) {
            elements.add(elm);
        }

        // 引数からなにか生み出すもの Producer
        // 型EのListに対して引数を入れていくので、引数はEの子クラスである必要がある
        public void pushAll(Iterable<? extends E> src) {
            for (E e : src) push(e);
        }

        public E pop() {
            int size = elements.size();
            if (size == 0) {
                throw new EmptyStackException();
            }
            return elements.remove(size - 1);
        }

        // 引数の状態を変えながら処理(消費する) Consumer
        // 引数はEを入れる入れ物にならなければ行けないので、Eの親クラスである必要がある
        // 個人的にはなかなか積極的にConsumerのようなものを作ろうとしないので、最初ピンとこなかった
        public void popAll(Collection<? super E> dst) {
            while(!elements.isEmpty()) {
                dst.add(pop());
            }
        }

        @Override
        public String toString() {
            return "Stack{" +
                    "elements=" + elements +
                    '}';
        }
    }
}
