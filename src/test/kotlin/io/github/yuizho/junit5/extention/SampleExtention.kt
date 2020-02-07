package io.github.yuizho.junit5.extention

import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Store


class SampleExtention : BeforeTestExecutionCallback, AfterTestExecutionCallback {
    override fun beforeTestExecution(context: ExtensionContext) {
        getStore(context).put("start", System.currentTimeMillis())
    }

    override fun afterTestExecution(context: ExtensionContext) {
        val testMethod = context.requiredTestMethod
        val preparation = testMethod.getAnnotation(Preparation::class.java) ?: null
        preparation?.also { println(it.toInsert()) }

        val startTime = getStore(context).remove("start", Long::class.java)
        val duration = System.currentTimeMillis() - startTime
        println("Method [${testMethod.name}] took $duration ms.")
    }

    private fun getStore(context: ExtensionContext): Store {
        // https://junit.org/junit5/docs/5.3.0/api/org/junit/jupiter/api/extension/ExtensionContext.Store.html
        return context.getStore(
                ExtensionContext.Namespace.create(javaClass, context.requiredTestMethod)
        )
    }
}

fun Preparation.toInsert(): List<Pair<String, List<String>>> = testData.flatMap { it.toInsert() }

fun Table.toInsert(): List<Pair<String, List<String>>> {
    return rows
            .map { it.toInsert() }
            .map {
                "INSERT INTO $name ${it.first}" to it.second
            }
}

fun Row.toInsert(): Pair<String, List<String>> {
    val keys = values.map { it.key }
    // TODO: prepared statementで渡せる形で持っていくように (型情報どうしようかな……)
    return "(${keys.joinToString(", ")}) VALUES (${keys.map { "?" }.joinToString(", ")})" to values.map { it.value }
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Preparation(val testData: Array<Table>)

annotation class Table(val name: String, val rows: Array<Row>)

annotation class Row(val values: Array<Column>)

annotation class Column(val key: String, val value: String)
