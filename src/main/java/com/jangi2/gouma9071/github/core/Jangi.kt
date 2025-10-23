package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.internet.P2PEventListener
import com.jangi2.gouma9071.github.internet.P2PManager
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.layout.VBox
import javafx.stage.Stage

// Application을 상속 및 P2PEventListener 인터페이스를 구현
class Jangi : Application(), P2PEventListener {

    private lateinit var p2pManager: P2PManager
    // GameState로 모든 게임 상태를 관리
    private var gameState: GameState = JangiGameReturn.resetState()

    // UI 컴포넌트
    private lateinit var gameRoomUI: GameRoomUI
    private val statusLabel = Label("게임을 시작하세요.")

    override fun start(primaryStage: Stage) {
        p2pManager = P2PManager(this)

        val root = BorderPane()
        gameRoomUI = GameRoomUI(gameState)

        // View에서 발생한 이동 요청을 Controller의 로직과 연결
        gameRoomUI.boardView.onMoveRequested = { moveAction ->
            handleLocalMove(moveAction.from, moveAction.to)
        }

        // 게임 UI를 중앙에 배치
        root.center = gameRoomUI

        // P2P 컨트롤 패널
        val ipTextField = TextField("127.0.0.1")
        val createGameButton = Button("방 만들기")
        val joinGameButton = Button("참가하기")

        createGameButton.setOnAction { p2pManager.startServer(9071) }
        joinGameButton.setOnAction { p2pManager.connectToServer(ipTextField.text, 9071) }

        val controlBox = VBox(10.0, statusLabel, ipTextField, createGameButton, joinGameButton)
        root.right = controlBox

        // 초기 상태 레이블 업데이트
        updateUI()

        val scene = Scene(root, 1000.0, 700.0) // 사이즈 조정
        primaryStage.title = "Jangi Game"
        primaryStage.scene = scene
        primaryStage.show()
    }

    // UI 업데이트를 담당할 함수
    private fun updateUI() {
        // statusLabel은 Jangi 클래스가 직접 관리
        statusLabel.text = "현재 턴: ${gameState.currentTeam}"
        // 나머지 게임 UI는 GameRoomUI에 위임
        gameRoomUI.update(gameState)
    }

    // 로컬 사용자의 이동을 처리
    private fun handleLocalMove(from: Position, to: Position) {
        val moveAction = MoveAction(from, to)
        val newGameState = JangiGameReturn.applyMove(gameState, moveAction)

        if (newGameState !== gameState) { // 상태가 변경되었다면 유효한 수
            gameState = newGameState
            p2pManager.sendMove(from, to)
            updateUI()
        } else {
            // 유효하지 않은 수에 대한 피드백 (예: 상태 표시줄 업데이트)
            statusLabel.text = "둘 수 없는 위치입니다! 현재 턴: ${gameState.currentTeam}"
        }
    }

    // --- P2PEventListener 구현부 ---

    override fun onConnected() {
        statusLabel.text = "연결 성공! 게임을 시작하세요."
        p2pManager.startListening()
    }

    override fun onMoveReceived(from: Position, to: Position) {
        val moveAction = MoveAction(from, to)
        gameState = JangiGameReturn.applyMove(gameState, moveAction)
        Platform.runLater { updateUI() } // UI 스레드에서 UI 업데이트
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
