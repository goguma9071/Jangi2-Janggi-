package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.internet.P2PEventListener
import com.jangi2.gouma9071.github.internet.P2PManager
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.stage.Stage

// Application을 상속 및 P2PEventListener 인터페이스를 구현
class Jangi : Application(), P2PEventListener {

    internal var currentTurn: team = team.楚
    private lateinit var p2pManager: P2PManager
    private val board = Board()

    private val statusLabel = Label("게임을 시작하세요.")

    override fun start(primaryStage: Stage) {
        p2pManager = P2PManager(this)

        val root = Pane()

        val boardImage = Image(javaClass.getResourceAsStream("/images/장기판.jpg"))
        val boardImageView = ImageView(boardImage)
        boardImageView.fitWidth = 800.0
        boardImageView.fitHeight = 600.0
        root.children.add(boardImageView)

        val ipTextField = TextField("127.0.0.1")
        val createGameButton = Button("방 만들기")
        val joinGameButton = Button("참가하기")

        createGameButton.setOnAction { p2pManager.startServer(9071) }
        joinGameButton.setOnAction { p2pManager.connectToServer(ipTextField.text, 9071) }

        val controlBox = VBox(10.0, statusLabel, ipTextField, createGameButton, joinGameButton)
        controlBox.relocate(820.0, 50.0)
        root.children.add(controlBox)

        val scene = Scene(root, 1000.0, 600.0)
        primaryStage.title = "Jangi Game"
        primaryStage.scene = scene
        primaryStage.show()
    }

    fun switchTurn() {
        currentTurn = if (currentTurn == team.楚) team.漢 else team.楚
        statusLabel.text = "현재 턴: $currentTurn"
    }

    private fun onMyPieceMoved(from: Position, to: Position) {
        board.movePiece(from, to)
        p2pManager.sendMove(from, to)
        switchTurn()
    }

    // --- P2PEventListener 구현부 ---

    override fun onConnected() {
        statusLabel.text = "연결 성공! 게임을 시작하세요."
        p2pManager.startListening()
    }

    override fun onMoveReceived(from: Position, to: Position) {
        statusLabel.text = "상대방이 수를 두었습니다."
        board.movePiece(from, to)
        switchTurn()
    }

    override fun onDisconnected() {
        statusLabel.text = "연결이 끊겼습니다."
    }

    override fun onError(message: String) {
        statusLabel.text = "오류: $message"
    }

    override fun stop() {
        p2pManager.close()
        super.stop()
    }
}

fun main() {
    Application.launch(Jangi::class.java)
}
