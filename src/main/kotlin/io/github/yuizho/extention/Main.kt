package io.github.yuizho.extention

fun main() {
    fun String.lengthIsEven(): Boolean {
        return this.length % 2 == 0
    }
    println("12".lengthIsEven())
    println("124".lengthIsEven())

    fun <T> Collection<T>.sizeIsEven(): Boolean {
        return this.size % 2 == 0
    }
    println(listOf<String>("a", "b").sizeIsEven())
    println(listOf<String>("a").sizeIsEven())
}

fun dummmy() {
    // ここではmainで定義した拡張関数は見えない
}