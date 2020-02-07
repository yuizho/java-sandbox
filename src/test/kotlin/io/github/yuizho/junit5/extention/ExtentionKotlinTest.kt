package io.github.yuizho.junit5.extention

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(SampleExtention::class)
class ExtentionKotlinTest {
    @Test
    @Preparation(testData = [
        Table(name = "hoge", rows = [
            Row([
                Column("id", "1"), Column("name", "hoge")
            ])
        ])
    ])
    fun test1() {
        Thread.sleep(50)
    }
}