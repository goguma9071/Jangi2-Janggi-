package com.jangi2.gouma9071.github.core

data class 車(override val team: team, override val position: Position) : Piece(team, position) {
    override val score = PieceScore.車.score
    override val pieceType = PieceType.車


    override fun getMovablePositions(board: Board): List<Position> {
        val movablePositions = mutableListOf<Position>()

        val directions = listOf(
            Position(0, -1), // Up
            Position(0, 1),  // Down
            Position(-1, 0), // Left
            Position(1, 0)   // Right
        )

        for (dir in directions) {
            var nextPos = Position(position.x + dir.x, position.y + dir.y)

            while (board.isWithinBounds(nextPos)) {
                val pieceAtNextPos = board.getPieceAt(nextPos)
                if (pieceAtNextPos == null) {
                    // The square is empty, so we can move here.
                    movablePositions.add(nextPos)
                } else {
                    // There is a piece in the way.
                    if (pieceAtNextPos.team != this.team) {
                        // It's an opponent's piece, so we can capture it.
                        movablePositions.add(nextPos)
                    }
                    // We can't move any further in this direction.
                    break
                }
                nextPos = Position(nextPos.x + dir.x, nextPos.y + dir.y)
            }
        }
        return movablePositions
    }
}