package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.core.*

/**
 * 이 객체는 상태를 가지지 않으며, 순수 함수들로만 구성됩니다.
 */
object JangiGame {

    /**
     * 현재 게임 상태(gameState)와 사용자의 움직임(moveAction)을 받아
     * 새로운 게임 상태를 반환하는 순수 함수입니다.
     */
    fun applyMove(gameState: GameState, moveAction: MoveAction): GameState {

        // 1. 이동 유효성 검사 (해당 기물이 움직일 수 있는 위치인지)
        // 2. 기물 이동 처리
        // 3. 기물 캡처 처리 (상대방 기물이 있는 경우)
        // 4. 점수 및 기물 개수 업데이트
        // 5. 턴 변경
        // 6. 새로운 GameState 객체 반환
        val movingPiece = gameState.board.getPieceAt(moveAction.from)
        val targetPiece = gameState.board.getPieceAt(moveAction.to)

        if (movingPiece == null) return gameState
        if (targetPiece != null && targetPiece.team == movingPiece.team) return gameState
        if (moveAction.to !in movingPiece.getMovablePositions(gameState.board)) return gameState
        if (movingPiece.team != gameState.currentTeam) return gameState

        val newPieceCount = gameState.pieceCount.toMutableMap()

        if (targetPiece != null) {
            val capturedTeam  = targetPiece.team
            val captureType = targetPiece.pieceType
            val mutablePieceCount = gameState.pieceCount.toMutableMap()

            val newBoard = gameState.board.movePiece(moveAction.from, moveAction.to)
            val newTurn = gameState.currentTeam.opposite()

            return gameState.copy(
                board = newBoard,
                currentTeam = newTurn,
                pieceCount = newPieceCount
            )

        }


        return gameState // 임시 반환
    }
}
