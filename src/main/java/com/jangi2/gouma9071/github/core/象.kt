package com.jangi2.gouma9071.github.core

class 象(team: team, override var position: Position) : Piece(team, position) {
    override val score = PieceScore.象.score
    override val piecetype = PieceType.象
    override fun getMovablePositions(board: Board): List<Position> {

        val movablePosition = mutableListOf<Position>()
        val moves = mapOf<Position, Pair<Position, Position>>(

            //상동
            Position(-2, -3) to Pair(Position(0, -1), Position(-1 ,-2)), // 좌상
            Position(2, -3) to Pair(Position(0, -1), Position(1 ,-2)), //우상
            //하동
            Position(-2, 3) to Pair(Position(0, 1), Position(-1 ,2)), // 좌하
            Position(2, 3) to Pair(Position(0, 1), Position(1 ,2)), // 우하
            //좌동
            Position(-3, -2) to Pair(Position(-1, 0), Position(-2 ,-1)),//좌상
            Position(-3, 2) to Pair(Position(-1, 0), Position(-2 ,1)), //좌하
            //우동
            Position(3, -2) to Pair(Position(1, 0), Position(2 ,-1)),// 우상
            Position(3, 2) to Pair(Position(1, 0), Position(2 ,1)) // 우하

        )
        for ((moves, myeoks) in moves) {
            val myeokPos1 = this.position + myeoks.first
            val myeokPos2 = this.position + myeoks.second
            val nextPos = this.position + moves
            if (board.isWithinBounds(myeokPos1) && board.getPieceAt(myeokPos1) == null &&
                board.isWithinBounds(myeokPos2) && board.getPieceAt(myeokPos2) == null) {

                if (board.isWithinBounds(nextPos)) {
                    val pieceAtNextPos = board.getPieceAt(nextPos)
                    if (pieceAtNextPos == null || pieceAtNextPos.team != this.team) { movablePosition.add(nextPos) }
                }
            }


        }
        return movablePosition
    }
}
