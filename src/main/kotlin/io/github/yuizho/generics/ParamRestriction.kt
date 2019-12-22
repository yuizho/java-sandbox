package io.github.yuizho.generics

// 型パラメータ制約
fun <T : Number> oneHalf(value: T): Double {
    return value.toDouble() / 2.0
}

fun main() {
    println(oneHalf(3))
}