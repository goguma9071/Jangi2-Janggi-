package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.core.*

/**
 * 이 객체는 상태를 가지지 않으며 순수 함수들로만 구성
 */

object JangiGame {

    /**
     * 현재 게임 상태(gameState)와 사용자의 움직임(moveAction)을 받아
     * 새로운 게임 상태를 반환하는 순수 함수
     */

    private fun calculatePieceCount(board: Board): Map<team, Map<PieceType, Byte>> {
        // 1. 양 팀 기물개수 저장용 map
        val pieceCount = mutableMapOf<team, MutableMap<PieceType, Byte>>()
        pieceCount[team.楚] = mutableMapOf()
        pieceCount[team.漢] = mutableMapOf()

        val allPieces = board.getAllPieces()
        for (piece in allPieces) {
            val teamMap = pieceCount[piece.team] !!
            teamMap[piece.pieceType] = ((teamMap[piece.pieceType] ?: 0) + 1).toByte()
        }
        return pieceCount.mapValues { it.value.toMap() }.toMap()
    }
    fun applyMove(gameState: GameState, moveAction: MoveAction): GameState {

        val movingPiece = gameState.board.getPieceAt(moveAction.from)
        val targetPiece = gameState.board.getPieceAt(moveAction.to)

        if (movingPiece == null) return gameState
        if (targetPiece != null && targetPiece.team == movingPiece.team) return gameState
        if (moveAction.to !in movingPiece.getMovablePositions(gameState.board)) return gameState
        if (movingPiece.team != gameState.currentTeam) return gameState

            val newBoard = gameState.board.movePiece(moveAction.from , moveAction.to)
            val newPieceCount = calculatePieceCount(newBoard)

            val newTurn = gameState.currentTeam.opposite()

            return gameState.copy(
                board = newBoard,
                currentTeam = newTurn,
                pieceCount = newPieceCount
            )

    }
}
