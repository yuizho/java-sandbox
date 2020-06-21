package io.github.yuizho.socket.nonblock

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.nio.charset.Charset

fun main() {
    // reference: https://www.kimullaa.com/entry/2016/12/09/000129
    ServerSocketChannel.open().use { ssc ->
        // configure as nonblocking
        ssc.configureBlocking(false)
        val port = 8080
        ssc.socket().bind(InetSocketAddress(port))
        Selector.open().use { selector ->
            // このチャンネルの現在の関心事を登録する
            // これからacceptすることが現在の関心事なので、OP_ACCEPTを入れる
            ssc.register(selector, SelectionKey.OP_ACCEPT)
            println("start server on port $port")
            while (selector.select() > 0) {
                val it = selector.selectedKeys().iterator()
                while (it.hasNext()) {
                    // pop
                    val key = it.next()
                    it.remove()

                    when {
                        key.isAcceptable -> {
                            accept(key.channel() as ServerSocketChannel, selector)
                        }
                        key.isReadable -> {
                            read(key.channel() as SocketChannel, selector)
                        }
                        key.isWritable -> {
                            write(key.channel() as SocketChannel, selector, key.attachment() as ByteArray)
                        }
                    }
                }
            }
        }
    }
}

fun accept(ssc: ServerSocketChannel, selector: Selector) {
    val channel = ssc.accept()
    println("connected by ${channel.socket().remoteSocketAddress}")
    channel.configureBlocking(false)
    channel.register(selector, SelectionKey.OP_READ)
}

fun read(channel: SocketChannel, selector: Selector) {
    val buffer = ByteBuffer.allocate(1024)
    val len = channel.read(buffer)
    if (len == -1) {
        println("the connection is disconnected by ${channel.socket().remoteSocketAddress}")
        channel.close()
        return
    }
    if (len == 0) {
        // 読み込めない場合は即時returnして別の仕事に回す
        // non blockingなので読み込めないときにblockされずここで0が返る
        return
    }

    buffer.flip()
    val bytes = ByteArray(buffer.limit())
    buffer.get(bytes)

    // なんか改行が入るのでprintで表示
    val line = bytes.toString(Charset.forName("UTF-8")).trim()
    println("received: $line by ${channel.socket().remoteSocketAddress}")

    if (line == "exit") {
        // exitが入力されていた場合はChannelを閉じる
        channel.close()
    } else {
        channel.register(selector, SelectionKey.OP_WRITE, bytes)
    }
}

fun write(channel: SocketChannel, selector: Selector, message: ByteArray) {
    val buffer = ByteBuffer.wrap(message)
    channel.write(buffer)
    // 現在のbufferのoffset位置から始まるByteBufferを複製する
    // つまりすべてのbufferを書ききれなかった場合に、書ききれなかった位置から始まる新しいByteBufferを作る
    val bufferCouldNotWrite = buffer.slice()

    if (bufferCouldNotWrite.hasRemaining()) {
        val remainingBytes = ByteArray(bufferCouldNotWrite.limit())
        bufferCouldNotWrite.get(remainingBytes)
        channel.register(selector, SelectionKey.OP_WRITE, remainingBytes)
    } else {
        // 全部書ききった場合再度READモードに戻る
        channel.register(selector, SelectionKey.OP_READ)
    }
}