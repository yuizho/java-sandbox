package io.github.yuizho.junit5

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

class MockitoTest {
    /**
     * lateinitをつけて後から値が満たされることを許容する
     */
    @InjectMocks
    lateinit var sampleController: SampleController

    @Mock
    lateinit var sampleService: SampleService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun test() {
        val mockedData = "this is the data returned by mockito"
        // whenは予約後なので`(バッククォート)で囲って上げる必要がある
        `when`(sampleService.findData()).thenReturn(mockedData)

        val actual = sampleController.get()

        assertEquals(actual, mockedData)
    }
}

class SampleController(private val sampleService: SampleService) {
    fun get(): String = sampleService.findData();
}

/**
 * Kotlinのクラス、メソッドはデフォルトがfinalなので、Mockitoとか使うときは
 * openをつけて継承を許可する必要がある。
 */
open class SampleService {
    open fun findData(): String = "this is the data returned by production"
}