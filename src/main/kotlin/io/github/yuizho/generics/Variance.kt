package io.github.yuizho.generics

open class Animal {
    fun feed() = println("餌をもらいました")
}

class Cat : Animal() {
    fun grooming() = println("毛づくろいしました")
}

// 群れクラス
// このクラスは動物の追加や変更を許していないので、共変にすることができる
// つまりこのクラスにおいてTはoutポジション(もどり値)にしか使えない
// inポジション(パラメータ)に使ったらコンパイルエラー
class Herd<out T : Animal>(
        private val animals: List<T>
) {
    val size: Int get() = animals.size
    operator fun get(i: Int): T = animals[i]
}

fun feedAll(animals: Herd<Animal>) {
    for (i in 0 until animals.size) {
        animals[i].feed()
    }
}

fun main() {
    val cats = Herd(listOf(Cat()))
    for (i in 0 until cats.size) {
        cats[i].grooming()
        feedAll(cats) // 共変になっていない場合ココでエラーとなる (Herd<Animal>しか入らない)
    }
}
