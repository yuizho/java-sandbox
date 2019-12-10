package io.github.yuizho.file

import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import java.util.*

fun main() {
    // write file to the folder same as this class
    // トップレベル関数のクラスの取得について
    // https://stackoverflow.com/questions/38230754/get-class-reference-from-top-level-function-in-kotlin

    // getResourceに空文字を入れるのがミソっぽい (そうするとこのクラスがおいてあるパッケージまでのパスが取れる)
    // ちなみにgetResourceに"/"を渡すと、classレベルののトップレベルのパス(..target/classesとか)が返る感じになる
    val path = object{}.javaClass.getResource("")?.path.let {
        Paths.get(it)
    }
    println(path)

    val dirPath = path.resolve("dirs").resolve(UUID.randomUUID().toString())
    // フォルダがなければ作るし、あれば空振りしてくれる
    // makdir -p xx に近い動作になる
    Files.createDirectories(dirPath)

    // BufferedWrieter, BufferedReaderによるファイルの書き出し &
    val filePath1 = dirPath.resolve("file1.txt")
    Files.newBufferedWriter(filePath1, Charset.forName("UTF-8"),StandardOpenOption.CREATE_NEW).use {
        it.write("the contents written by bufferedWriter")
    }
    val file1Content = Files.newBufferedReader(filePath1, Charset.forName("UTF-8")).use {
        it.readText()
    }
    println(file1Content)

    // WriteString, ReadStringによるファイルの書き出し (Java11環境からt使える)
    val filePath2 = dirPath.resolve("file2.txt")
    Files.writeString(filePath2, "the contents written by writeString")
    val file2Content = Files.readString(filePath2)
    println(file2Content)
}