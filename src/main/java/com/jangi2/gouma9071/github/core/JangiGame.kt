package com.jangi2.gouma9071.github.core



class JangiGame {

    fun applyMove(gameState: GameState, moveAction: MoveAction): GameState {
        val nextTeam = if (gameState.currentTeam == com.jangi2.gouma9071.github.core.team.楚) team.漢 else team.楚
        val pieceToMove = gameState.board.getPieceAt(moveAction.from)
        val newBoard = gameState.board.copy()

        when (pieceToMove == null || !pieceToMove.getMovablePositions(gameState.board).contains(moveAction.to)
                || pieceToMove.team != gameState.currentTeam) {

            true -> return gameState

            else -> {
                newBoard.movePiece(moveAction.from, moveAction.to)

                val capturedPiece = gameState.board.getPieceAt(moveAction.to)
                val newScores = gameState.score
                var newPieceCounts = gameState.pieceCount

                if (capturedPiece != null && capturedPiece.team != gameState.currentTeam) {

                }
            }
        }
        return gameState.copy(
            board = newBoard,
            currentTeam = nextTeam
        )
    }

}