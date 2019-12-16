package io.github.yuizho.operator

fun main() {
    val p1 = Point(10, 20)
    val p2 = Point(30, 40)
    println(p1 + p2)

    var point = p1
    point += p2
    println(point)

    println(-point)
}

data class Point(val x: Int, val y: Int) {
    /**
     * "+"演算子のオーバーロード
     */
    operator fun plus(other: Point): Point {
        return Point(x + other.x, y + other.y)
    }

    /**
     * 単項目演算子 "-"のオーバーロード
     */
    operator fun unaryMinus(): Point {
        return Point(-x, -y)
    }
}