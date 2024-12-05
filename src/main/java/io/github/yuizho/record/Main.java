package io.github.yuizho.record;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // https://dev.java/learn/records/

        System.out.println("--------- Basic Features ---------");
        var p1 = new Point(1, 2);
        var p2 = new Point(1, 2);
        System.out.println(p1.equals(p2)); // true

        try {
            var p3 = new Point(-1, 2);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // x and y must be positive
        }

        // grouping by stream
        System.out.println("--------- Stream ---------");
        Map<Integer, Long> map = Stream.of(
                new Point(1, 1),
                new Point(1, 2),
                new Point(3, 4),
                new Point(3, 7),
                new Point(3, 4),
                new Point(6, 6)
        ).collect(
                Collectors.groupingBy(Point::x, Collectors.counting()
        ));
        System.out.println(map);

        // Java21から入ったRecord Patterns
        System.out.println("--------- Pattern Matching ---------");
        System.out.println(patternMatchingJava21(new Healthy()));
        System.out.println(patternMatchingJava21(new Unhealthy("busy")));
        System.out.println(patternMatchingJava21(new Unhealthy(null)));
        System.out.println(patternMatchingJava21(new Stopped(LocalDateTime.now())));
        System.out.println(patternMatchingJava21(null));
    }

    public record Point(int x, int y) {
        public static String STATIC_FIELD;
        static {
            STATIC_FIELD = "static field in Point";
        }

        // canonical constructor
        /*
        public Point(int x, int y) {
            // validationや色々したい場合とかに明示的なコンストラクタもかける
            this.x = x < 0 ? 0 : x;
            this.y = y < 0 ? 0 : y;
        }
        */

        // compact constructor
        public Point {
            // validationとかしたい場合はここに書く
            if (x < 0 || y < 0) {
                throw new IllegalArgumentException("x and y must be positive");
            }
        }

        public void setX(int x) {
            // compile error (final)
            // this.x = x;
        }
    }

    // compile error (継承不可)
    //public static class Hoge extends Point { }

    sealed interface State permits Healthy, Unhealthy, Stopped {
    }

    record Healthy() implements State {
    }

    record Unhealthy(String reason) implements State {
    }

    record Stopped(LocalDateTime stoppedDateTime) implements State {
    }

    static String patternMatchingJava21(State state) {
        return switch (state) {
            case null -> "null";
            case Healthy() -> "Healthy";
            case Unhealthy(String r) when r == null -> "Unhealthy: null";
            case Unhealthy(String r) -> "Unhealthy: " + r;
            case Stopped(LocalDateTime d) -> "Stopped: " + d;
            //case Point(int x, int y) -> "Point: " + x + ", " + y // compile error
        };
    }
}
