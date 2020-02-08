package io.github.yuizho.junit5.extention

import org.junit.jupiter.api.extension.AfterTestExecutionCallback
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ExtensionContext.Store
import java.sql.Connection
import javax.sql.DataSource

class SampleExtention(val datasSource: DataSource) : BeforeTestExecutionCallback, AfterTestExecutionCallback {
    override fun beforeTestExecution(context: ExtensionContext) {
        getStore(context).put("start", System.currentTimeMillis())
        val testMethod = context.requiredTestMethod
        val preparation = testMethod.getAnnotation(Preparation::class.java) ?: return

        val conn = datasSource.connection
        // delete
        preparation.toDeleteQuerySources().executeQueries(conn)
        // insert data
        preparation.toInsertQuerySources().executeQueries(conn)
    }

    override fun afterTestExecution(context: ExtensionContext) {
        val testMethod = context.requiredTestMethod
        // TODO: Methodから取れなかったらClassから取るようにしたい
        val preparation = testMethod.getAnnotation(Preparation::class.java) ?: return

        val conn = datasSource.connection
        // delete
        preparation.toDeleteQuerySources().executeQueries(conn)

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

data class QuerySource(val sql: String, val params: List<String>)
data class QuerySourceList(val querySources: List<QuerySource>) {
    fun executeQueries(conn: Connection) {
        querySources.forEach { sqlSource ->
            println(sqlSource)
            conn.prepareStatement(sqlSource.sql).use { ps ->
                sqlSource.params.forEachIndexed { i, elm ->
                    // TODO: によって分けられるようにしたい
                    // https://c-jdbc.ow2.org/current/doc/doxygen/english/html/classorg_1_1objectweb_1_1cjdbc_1_1driver_1_1PreparedStatement.html#a22
                    ps.setString(i + 1, elm)
                }
                ps.executeUpdate()
            }
        }
    }
}


fun Preparation.toInsertQuerySources(): QuerySourceList = QuerySourceList(testData.flatMap { it.toInsertQuerySources() })
fun Preparation.toDeleteQuerySources(): QuerySourceList {
    return QuerySourceList(testData.flatMap { it.toDeleteQuerySources() }
            // 外部キー制約があるケースを考慮して、ここで対象TableのListをreverseし、
            // insert時と逆に子供のテーブルからdeleteする
            .reversed())
}

fun Table.toInsertQuerySources(): List<QuerySource> {
    return rows
            .map { it.toValuesSyntax() }
            .map {
                QuerySource(
                        sql = "INSERT INTO $name ${it.first}",
                        params = it.second
                )
            }
}

fun Table.toDeleteQuerySources(): List<QuerySource> {
    return rows
            .map { it.toWhereSyntax() }
            .map {
                QuerySource(
                        sql = "DELETE FROM $name WHERE ${it.first}",
                        params = it.second
                )
            }
}

fun Row.toValuesSyntax(): Pair<String, List<String>> {
    val keys = vals.map { it.name }
    // TODO: prepared statementで渡せる形で持っていくように (型情報どうしようかな……)
    return "(${keys.joinToString(", ")}) VALUES (${keys.map { "?" }.joinToString(", ")})" to vals.map { it.value }
}

fun Row.toWhereSyntax(): Pair<String, List<String>> {
    val ids = vals.filter { it.isId }
    val conditions = ids.map { "${it.name} = ?" }
    // TODO: prepared statementで渡せる形で持っていくように (型情報どうしようかな……)
    return "${conditions.joinToString(" AND ")}" to ids.map { it.value }
}

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class Preparation(val testData: Array<Table>)

annotation class Table(val name: String, val rows: Array<Row>)

annotation class Row(val vals: Array<Col>)

annotation class Col(val name: String, val value: String, val isId: Boolean = false)

// annotation class Type(val name: String, val type: Types)
