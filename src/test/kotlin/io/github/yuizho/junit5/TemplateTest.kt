package io.github.yuizho.junit5

import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.*
import java.util.stream.Stream
import java.util.Collections


class TemplateTest {
    @TestTemplate
    @ExtendWith(MyTestTemplateInvocationContextProvider::class)
    fun testWithTemplate(param: String) {
        println(param)
    }
}

class MyTestTemplateInvocationContextProvider
    : TestTemplateInvocationContextProvider {
    override fun supportsTestTemplate(p0: ExtensionContext?): Boolean = true

    override fun provideTestTemplateInvocationContexts(context: ExtensionContext?): Stream<TestTemplateInvocationContext> {
        // ココで指定した引数が毎回テストメソッドの引数として渡される
        // 同じ引数でテストしたり、たくさんの引数のパターンを用意したり、同じコンテキストで何回もテストが実行サれる場合に使われる想定っぽい
        return Stream.of(
                getInvocationContext("parameter-1"),
                getInvocationContext("parameter-2")
        )
    }

    fun getInvocationContext(parameter: String): TestTemplateInvocationContext {
        // 無名クラスの実装
        return object : TestTemplateInvocationContext {
            override fun getDisplayName(invocationIndex: Int): String = parameter

            override fun getAdditionalExtensions(): List<Extension> {
                return Collections.singletonList(
                        object : ParameterResolver {
                            override fun supportsParameter(
                                    parameterContext: ParameterContext?,
                                    extentionContext: ExtensionContext?): Boolean {
                                return parameterContext?.parameter?.type == String::class.java
                            }

                            override fun resolveParameter(
                                    parameterContext: ParameterContext?,
                                    extentionContext: ExtensionContext?): Any {
                                return parameter
                            }

                        }
                )
            }
        }
    }
}