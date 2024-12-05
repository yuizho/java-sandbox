package io.github.yuizho.nio.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

public class FileOperationBasics {
    public static void main(String... args) throws IOException {
        Path file1 = Path.of(
                Optional.ofNullable(
                                FileSystemBasics.class.getClassLoader().getResource("dir/test.txt")
                        )
                        .orElseThrow(() -> new NoSuchFileException("file not found"))
                        .getPath()
        );

        // Reading and Writing Small FIles
        System.out.println("--------- Reading and Writing Small FIles");
        Files.readAllLines(file1).forEach(System.out::println);

        Path file2 = file1.getParent().resolve("test2.txt");
        Files.writeString(
                file2,
                LocalDateTime.now().toString()
        );
        System.out.println(Files.readString(file2));

        Path tempFile = Files.createTempFile(null, ".txt");
        System.out.println(tempFile);

        // Reading and Writintg Text Files
        System.out.println("--------- Reading and Writintg Text Files");
        try(var reader = Files.newBufferedReader(file1, java.nio.charset.StandardCharsets.UTF_8);
            var lines = reader.lines()) {
            lines.forEach(System.out::println);
        }

        // Writing and Reading Characters to Binary Streams
        System.out.println("--------- Writing and Reading Characters to Binary Streams");
        try (var inputStream = Files.newInputStream(file1);
             var reader = new BufferedReader(new InputStreamReader(inputStream));
             var lines = reader.lines()) {
            lines.forEach(System.out::println);
        }

        // Handling Compressed Binary Streams
        System.out.println("--------- Handling Compressed Binary Streams");
        var gzipFile = file1.getParent().resolve("test2.txt.gz");
        String result;
        try (var inputStream = Files.newInputStream(gzipFile);
             var gzipInputStream = new GZIPInputStream(inputStream);
             var reader = new InputStreamReader(gzipInputStream);
             var bufferedReader = new BufferedReader(reader);
             var stream = bufferedReader.lines();) {
            result = stream.collect(Collectors.joining("\n"));
        }
        System.out.println(result);

        // Reading and Writing Strings of Characters
        System.out.println("--------- Reading and Writing Strings of Characters");
        try (var reader = new BufferedReader(new StringReader("hogehoge\nfugafuga"));
             var lines = reader.lines()) {
            lines.forEach(System.out::println);
        }
    }
}
