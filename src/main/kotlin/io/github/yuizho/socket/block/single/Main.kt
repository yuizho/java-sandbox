package io.github.yuizho.socket.block.single

import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel

fun main() {
    ServerSocketChannel.open().use { ssc ->
        val port = 8080
        ssc.socket().bind(InetSocketAddress(port))
        println("start server on port $port")
        while (true) {
            println("waiting a request...")
            // ここでブロックするのでこのサーバは、一回に一つのクライアントしか相手に出来ない
            // 複数のterminalでtelnetすると、2回目に接続したクライアントは１つ目のクライアントがexitするまでブロックされてしまう
            ssc.accept().use { sc ->
                println("a client has connected to this server")
                sc.socket().getInputStream().bufferedReader().use { reader ->
                    sc.socket().getOutputStream().bufferedWriter().use { writer ->
                        while (true) {
                            val line = reader.readLine()
                            println("received: $line")
                            if ("exit" == line) {
                                sc.socket().close()
                                break
                            }
                            // echo
                            writer.write("$line\n")
                            writer.flush()
                        }
                        println("the client has disconnected")
                    }
                }
            }
        }
    }
}