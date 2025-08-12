package com.jangi2.gouma9071.github.core



class 卒(team: team, override var position: Position ) : Piece(team, position) {
    override val score = PieceScore.卒.score
    override val piecetype = PieceType.卒


    override fun getMovablePositions(board: Board): List<Position> {
        val movablePosition = mutableListOf<Position>()

        val directions = mutableSetOf<Position>()
        directions.add(Position(-1, 0)) //좌동
        directions.add(Position(1, 0 )) //우동

        if (this.team == com.jangi2.gouma9071.github.core.team.楚) {
            directions.add(Position(0, 1))
            if (board.isInsidePalace(this.position)) {
                directions.add(Position(1, 1))
                directions.add(Position(-1, 1))
            }
        } else {
            directions.add(Position(0, -1))
            if (board.isInsidePalace(this.position)) {
                directions.add(Position(-1, -1))
                directions.add(Position(1, -1))
            }
        } //한나라 일시

        for (dir in directions) {
            val nextPos = this.position + dir

            if (board.isWithinBounds(nextPos)) {
                val pieceAtNextPos = board.getPieceAt(nextPos)

                if (pieceAtNextPos == null) {
                    movablePosition.add(nextPos)
                } else {
                    if (pieceAtNextPos.team != this.team) {
                        movablePosition.add(nextPos)
                    }
                }
            }
        }
        return movablePosition
    }
}
