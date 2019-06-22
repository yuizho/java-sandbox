package io.github.yuizho.clone;

import java.util.Arrays;

public class Main {
    public static void main(String... args) {
        SimpleBean simpleBean = new SimpleBean(1);
        SimpleBean copied = simpleBean.clone();
        simpleBean.setId(2);
        System.out.println(simpleBean);
        System.out.println(copied);

        ComplecatedBean complecatedBean = new ComplecatedBean();
        complecatedBean.numbers = new Integer[] {1, 2, 3};
        ComplecatedBean copiedComplecatedBean = complecatedBean.clone();
        copiedComplecatedBean.numbers[0] = 0;
        System.out.println(complecatedBean);
        System.out.println(copiedComplecatedBean);
    }

    /**
     * Cloneableを実装しないとCloneNotSupportedExceptionが発生する
     */
    static class SimpleBean implements Cloneable {
        private int id;
        public SimpleBean(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "SimpleBean{" +
                    "id=" + id +
                    '}';
        }

        @Override
        public SimpleBean clone() {
            try {
                return (SimpleBean) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    static class ComplecatedBean implements Cloneable {
        Integer[] numbers;

        @Override
        public String toString() {
            return "ComplecatedBean{" +
                    "numbers=" + Arrays.toString(numbers) +
                    '}';
        }

        @Override
        public ComplecatedBean clone() {
            try {
                ComplecatedBean complecatedBean
                        = (ComplecatedBean) super.clone();
                // 配列の場合はこれでうまくコピーされる
                complecatedBean.numbers = numbers.clone();
                return complecatedBean;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}
