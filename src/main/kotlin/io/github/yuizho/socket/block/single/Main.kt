package io.github.yuizho.socket.block.single

import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel

fun main() {
    ServerSocketChannel.open().use { ssc ->
        val port = 8080
        ssc.socket().bind(InetSocketAddress(port))
        println("start server on port $port")
        while (true) {
            ssc.accept().use { sc ->
                sc.socket().getInputStream().bufferedReader().also { reader ->
                    reader.forEachLine { line ->
                        println("received: $line")
                        if ("exit" == line) {
                            sc.socket().close()
                            return@forEachLine
                        }
                        sc.socket().getOutputStream().bufferedWriter().also { writer ->
                            writer.write("$line\n")
                            writer.flush()
                        }
                    }
                }
            }
        }
    }
}