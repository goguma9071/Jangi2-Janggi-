package com.jangi2.gouma9071.github.core

import javafx.scene.layout.BorderPane

/**
 * 게임 방 하나(탭 하나)의 전체 UI를 관리하는 컨테이너.
 * 이 클래스는 내부에 BoardView와 SidePanelView를 포함하게 된다.
 */
class GameRoomUI(private var gameState: GameState) : BorderPane() {

    val boardView = BoardView(gameState)
    private val sidePanelView = SidePanelView(gameState)

    init {
        // 중앙에는 BoardView 배치
        setCenter(boardView)

        // 오른쪽에는 SidePanelView 배치
        setRight(sidePanelView)
    }

    /**
     * 새로운 GameState를 받아 UI 전체를 업데이트하는 함수
     */
    fun update(newGameState: GameState) {
        this.gameState = newGameState
        boardView.update(newGameState)
        sidePanelView.update(newGameState)
    }
}
