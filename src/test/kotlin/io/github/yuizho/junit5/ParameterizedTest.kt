package io.github.yuizho.junit5

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.*
import java.util.stream.Stream
import kotlin.test.assertNotNull

class ValueSourceStringsParameterizedTest {
    @DisplayName("ValueSourceStringsParameterizedTest")
    @ParameterizedTest(name = "[{index} first argument={0}]")
    @ValueSource(strings = ["Hello", "World"])
    fun testWith(argument: String) {
        println("Parameterized test with (String) parameter: $argument")
        assertNotNull(argument)
    }
}

class ValueSourcePrimitiveTypesParameterizedTest {
    @ParameterizedTest
    @ValueSource(ints = [0, 1])
    fun testWith(argument: Int) {
        println("Parameterized test with (Int) parameter: $argument")
        assertNotNull(argument)
    }

    @ParameterizedTest
    @ValueSource(doubles = [2.2, 3.3])
    fun testWith(argument: Double) {
        println("Parameterized test with (double) parameter: $argument")
        assertNotNull(argument)
    }
}

class EnumSourceParameterizedTest {
    enum class Gender(val value: String) {

        MEN("男性"),
        WOMEN("女性"),
        /**
         * https://twitter.com/freekoala5/status/1140542476941266945
         */
        OTHERS("その他")
    }

    @ParameterizedTest
    @EnumSource(Gender::class)
    fun testWithAllOf(argument: Gender) {
        println("Parameterized test with (Gender) parameter: $argument")
        assertNotNull(argument)
    }

    @ParameterizedTest
    @EnumSource(Gender::class,
            mode = EnumSource.Mode.EXCLUDE,
            names = ["MEN", "WOMEN"])
    fun testWithPartOf(argument: Gender) {
        println("Parameterized test with part of (Gender) parameter: $argument")
        assertNotNull(argument)
    }
}

class MethodSourceTypesParameterizedTest {
    companion object {
        /**
         * MethodSourceはTestInstanceを指定しない場合、staticじゃないとだめ。
         * https://oohira.github.io/junit5-doc-jp/user-guide/#writing-tests-parameterized-tests-sources-MethodSource
         *
         * Kotlinで知ってるクラス内にstaticメソッド作るには@JvmStaticつければいいみたい (通常はSingletonになる)
         * https://qiita.com/boohbah/items/167233c7eafe17f3150b#companion%E3%82%AA%E3%83%96%E3%82%B8%E3%82%A7%E3%82%AF%E3%83%88
         */
        @JvmStatic fun intProvider(): List<Int> = listOf(0, 1)
        @JvmStatic fun argumentsProvider(): List<Arguments> {
            return listOf(
                    Arguments.of("山田", 1),
                    Arguments.of("石川", 19)
            )
        }
    }

    @ParameterizedTest
    @MethodSource("intProvider")
    fun testWith(argument: Int) {
        println("Parameterized test with (Int) parameter: $argument")
        assertNotNull(argument)
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    fun testWith(name: String, number: Int) {
        println("Parameterized test with (Arguments) parameter: $number: $name")
        assertNotNull(String)
        assertNotNull(number)
    }
}

class CsvSourceParameterizedTest {
    @ParameterizedTest
    @CsvSource(value = ["yamada, 1", "ishikawa, 19"])
    fun testWith(name: String, number: Int) {
        println("Parameterized test with (csv) parameter: $number: $name")
        assertNotNull(String)
        assertNotNull(number)
    }
}

class ArgumentSourceParameterizedTest {
    class CustomArgumentProvider : ArgumentsProvider {
        override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
            return Stream.of(
                    Arguments.of("山田", 1),
                    Arguments.of("石川", 19)
            )
        }
    }

    @ParameterizedTest
    @ArgumentsSource(CustomArgumentProvider::class)
    fun testWith(name: String, number: Int) {
        println("Parameterized test with (ArgumentSource) parameter: $number: $name")
        assertNotNull(String)
        assertNotNull(number)
    }
}