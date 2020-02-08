package io.github.yuizho.junit5.extention

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.mariadb.jdbc.MariaDbDataSource


class ExtentionKotlinTest {
    companion object {
        @JvmField
        @RegisterExtension
        val sampleExtention = SampleExtention(
                datasSource = MariaDbDataSource().also {
                    it.setUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=UTF-8")
                    it.user = "test"
                    it.setPassword("password")
                }
        )
    }

    @Test
    @Preparation([
        Table("division", [
            Row([
                Col("id", "3", true),
                Col("name", "テスト")
            ])
        ]),
        Table("product", [
            Row([
                Col("id", "2", true),
                Col("division", "3"),
                Col("created", "2019-12-12 00:01:01"),
                Col("name", "bb")
            ])
        ])
    ])
    fun test1() {
    }
}