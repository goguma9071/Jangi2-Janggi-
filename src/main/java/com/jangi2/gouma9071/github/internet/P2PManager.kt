package com.jangi2.gouma9071.github.internet

import com.jangi2.gouma9071.github.core.Position
import kotlinx.coroutines.*

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class P2PManager(private val listener: P2PEventListener) {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var socket: Socket? = null
    private var writer: PrintWriter? = null

    fun startServer(port: Int) {
        scope.launch {
            try {
                val serverSocket = ServerSocket(port)
                socket = serverSocket.accept()
                setupConnection()
            } catch (e: Exception) {
                if (e is CancellationException) throw e // 코루틴 취소는 정상 처리
                withContext(Dispatchers.Main) { listener.onError("서버 오류: ${e.message}") }
            }
        }
    }

    fun connectToServer(ip: String, port: Int) {
        scope.launch {
            try {
                socket = Socket(ip, port)
                setupConnection()
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                withContext(Dispatchers.Main) { listener.onError("연결 오류: ${e.message}") }
            }
        }
    }

    private suspend fun setupConnection() {
        writer = PrintWriter(socket!!.getOutputStream(), true)
        withContext(Dispatchers.Main) {
            listener.onConnected()
        }
    }

    fun startListening() {
        scope.launch {
            try {
                val reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                while (scope.isActive) {
                    val message = reader.readLine() ?: break
                    if (message.startsWith("MOVE")) {
                        val parts = message.split(" ")[1].split(",")
                        val from = Position(parts[0].toInt(), parts[1].toInt())
                        val to = Position(parts[2].toInt(), parts[3].toInt())
                        withContext(Dispatchers.Main) {
                            listener.onMoveReceived(from, to)
                        }
                    }
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                withContext(Dispatchers.Main) { listener.onError("수신 오류: ${e.message}") }
            } finally {
                withContext(Dispatchers.Main) { listener.onDisconnected() }
            }
        }
    }

    fun sendMove(from: Position, to: Position) {
        scope.launch {
            writer?.println("MOVE ${from.x},${from.y} ${to.x},${to.y}")
        }
    }

    fun close() {
        scope.coroutineContext[Job]?.cancel()
        socket?.close()
    }
}
