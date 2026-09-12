package io.github.yuizho.process;


import java.io.InputStream;
import java.util.Scanner;

public class Main {
    public static void main(String... args) throws Exception {
        var pb = new ProcessBuilder( "java", "-version");
        var process = pb.start();

        // プロセスの実行
        int ret = process.waitFor();

        // 標準出力
        System.out.println("--------標準出力");
        printInputStream(process.getInputStream());
        // 標準エラー出力
        System.out.println("--------標準エラー出力");
        printInputStream(process.getErrorStream());

        System.out.println("--------結果コード");
        System.out.println(ret);
    }

    private static void printInputStream(InputStream is) {
        try (var scanner = new Scanner(is)) {
            while(scanner.hasNextLine()) {
                var line = scanner.nextLine();
                System.out.println(line);
            }
        }
    }
}
