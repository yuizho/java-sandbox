package io.github.yuizho.nio.file;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

public class FileSystemBasics {
    public static void main(String... args) throws IOException {
        Path p1 = Path.of(
                Optional.ofNullable(
                        FileSystemBasics.class.getClassLoader().getResource("dir/test.txt")
                )
                        .orElseThrow(() -> new NoSuchFileException("file not found"))
                        .getPath()
        );

        // Manipulating Files and Directories
        System.out.println(
                Files.exists(p1)
        );
        Files.copy(p1, p1.getParent().resolve("test2.txt"), java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // Managing Files Attirbutes
        System.out.println(
                Files.size(p1)
        );
        System.out.println(
                Files.isDirectory(p1.getParent())
        );

        // Listing the Content of a Directory
        System.out.println("---------- Listing the Content of a Directory");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(p1.getParent(), "*.txt")) {
            for (Path file : stream) {
                System.out.println(file.getFileName());
            }
        }

        // Walking the File Tree
        System.out.println("---------- Walking the File Tree");
        var matcher = FileSystems.getDefault().getPathMatcher("glob:*.{txt,csv}");
        SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                System.out.println("- " + dir.getFileName());
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (matcher.matches(file.getFileName())) {
                    System.out.println("  - " + file.getFileName());
                }
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(p1.getParent(), visitor);
    }
}
