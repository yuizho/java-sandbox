package io.github.yuizho.junit5.extention

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith


@ExtendWith(SampleExtention::class)
class ExtentionTest {
    @Test
    fun test1() {
        Thread.sleep(50)
    }
}