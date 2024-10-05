package io.github.yuizho.nio.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Optional;

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
    }
}
