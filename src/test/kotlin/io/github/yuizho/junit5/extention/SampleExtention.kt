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