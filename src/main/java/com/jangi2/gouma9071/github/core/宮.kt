package com.jangi2.gouma9071.github.core

class 宮(team: team, override var position: Position) : Piece(team, position) {
    override val score = PieceScore.宮.score
    override val piecetype = PieceType.宮
    override fun getMovablePositions(board: Board): List<Position> {

        val movablePosition = mutableListOf<Position>()
        val directions = mutableSetOf<Position>()

        if (board.isInsidePalace(this.position)) {
            directions.add(Position(-1, 0))
            directions.add(Position(1, 0))
            directions.add(Position(0, 1))
            directions.add(Position(0, -1))
            directions.add(Position(1, 1))
            directions.add(Position(-1, 1))
            directions.add(Position(-1, -1))
            directions.add(Position(1, -1))
        }
        for (dir in directions) {
            val nextPos = this.position + dir

            if (board.isWithinBounds(nextPos) && board.isInsidePalace(nextPos)) {
                val pieceAtNextPos = board.getPieceAt(nextPos)
                if (pieceAtNextPos == null || pieceAtNextPos.team != this.team) {
                    movablePosition.add(nextPos)
                }
            }
        }
        return movablePosition
    }
}
