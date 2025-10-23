package com.jangi2.gouma9071.github.core

import javafx.geometry.Insets
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Font

/**
 * 점수, 잡은 기물 등 게임 진행 상황을 표시하는 UI 컴포넌트.
 */
class SidePanelView(private var gameState: GameState) : VBox(10.0) {

    private val choScoreLabel = Label()
    private val hanScoreLabel = Label()

    // TODO: 잡은 기물 목록을 표시할 UI 요소 추가
    fun ViewCapturedPiece() {

        val capturedPieces = gameState.pieceCount



    }

    init {
        padding = Insets(15.0)
        style = "-fx-background-color: #f0f0f0;"

        val title = Label("게임 정보")
        title.font = Font.font(20.0)

        children.addAll(title, choScoreLabel, hanScoreLabel)

        updateView()
    }

    /**
     * 현재 GameState를 기반으로 각 팀의 점수를 다시 계산하고 UI를 업데이트한다.
     */
    private fun updateView() {
        // 점수 계산: 남아있는 기물의 점수를 합산
        var choScore = 0f
        var hanScore = 0f

        gameState.board.getAllPieces().forEach {
            if (it.team == team.楚) {
                choScore += it.score
            } else {
                hanScore += it.score
            }
        }

        choScoreLabel.text = "초(楚) 점수: $choScore"
        hanScoreLabel.text = "한(漢) 점수: $hanScore"

        // TODO: 잡은 기물 목록 UI 업데이트
    }

    /**
     * 새로운 GameState를 받아 UI를 업데이트하는 외부 공개 함수
     */
    fun update(newGameState: GameState) {
        this.gameState = newGameState
        updateView()
    }
}
