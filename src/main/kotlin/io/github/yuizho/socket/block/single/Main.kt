package io.github.yuizho.socket.block.single

import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel

fun main() {
    ServerSocketChannel.open().use { ssc ->
        ssc.socket().bind(InetSocketAddress(8080))
        println("start server")
        while (true) {
            ssc.accept().use { sc ->
                sc.socket().getInputStream().bufferedReader().use { reader ->
                    reader.forEachLine { line ->
                        println("received: $line")
                        if ("exit" == line) {
                            sc.socket().close()
                        }
                        sc.socket().getOutputStream().bufferedWriter().use { writer ->
                            writer.write(line!!)
                        }
                    }

                }
            }
        }
    }
}