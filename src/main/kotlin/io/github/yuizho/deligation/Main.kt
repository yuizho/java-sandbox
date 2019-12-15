package io.github.yuizho.deligation

class CountingSet<T>(
        private val innerSet: MutableCollection<T> = HashSet<T>()
) : MutableCollection<T> by innerSet {
    var objectsAdded = 0

    override fun add(element: T): Boolean {
        objectsAdded += 1
        return innerSet.add(element)
    }

    override fun addAll(c: Collection<T>): Boolean {
        objectsAdded += c.size
        return innerSet.addAll(c)
    }
}

fun main() {
    val cSet = CountingSet<Int>()
    cSet.addAll(listOf(1, 1, 2))
    println("${cSet.objectsAdded} objects were added, ${cSet.size} remain")
}