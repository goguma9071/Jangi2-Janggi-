package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.core

object JangiGame {
  /** 코드 효율성 위해 스칼라로 작성 */
  def applyMove(gameState: GameState, moveAction: MoveAction): GameState ={
    val board = gameState.getBoard()
    val currentTeam = gameState.getCurrentTeam()
    val pieceToMove = Option(board.getPieceAt(moveAction.getFrom()))
    // 구현 할 것

    gameState //임시 반환. 추 후
  }
}