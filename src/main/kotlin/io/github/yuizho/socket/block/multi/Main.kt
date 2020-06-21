package io.github.yuizho.socket.block.multi

import java.net.InetSocketAddress
import java.net.Socket
import java.nio.channels.ServerSocketChannel
import java.util.concurrent.Executors

fun main() {
    val executor = Executors.newFixedThreadPool(3)
    ServerSocketChannel.open().use { ssc ->
        val port = 8080
        ssc.socket().bind(InetSocketAddress(port))
        println("start server on port $port")
        while (true) {
            println("waiting a request...")
            // ソケットは各Thread内で閉じるようにする。
            // 実際の処理は別Tread内で行うので、Threadの数のぶん、複数のクライアントを同時に処理できる。
            // 同時に接続数が、executorの数に達した場合は、どこかのThreadが空くまで待ちが発生してしまう。
            val socket = ssc.socket().accept()
            println("handle on a worker tread")
            executor.submit(Task(socket))
        }
    }
}

class Task(private val socket: Socket) : Runnable {
    override fun run() {
        println("a client has connected to this server. this connection is handled on thread ${Thread.currentThread().id}")
        try {
            socket.getInputStream().bufferedReader().use { reader ->
                socket.getOutputStream().bufferedWriter().use { writer ->
                    while (true) {
                        val line = reader.readLine()
                        println("received: $line")
                        if ("exit" == line) {
                            break
                        }
                        // echo
                        writer.write("$line\n")
                        writer.flush()
                    }
                }
            }
        } finally {
            socket.close()
            println("the client has disconnected")
        }
    }
}