package com.jangi2.gouma9071.github.core



data class 馬(override val team: team, override val position: Position) : Piece(team, position) {
    override val score = PieceScore.馬.score
    override val pieceType = PieceType.馬
    override fun getMovablePositions(board: Board): List<Position> {

        val movablePosition = mutableListOf<Position>()
        val moves = mapOf<Position, Position>(

            Position(-1, -2) to Position(0, -1), // 좌상
            Position(1, -2)  to Position(0, -1), // 우상
            Position(-1, 2)  to Position(0, 1),  // 좌하
            Position(1, 2)   to Position(0, 1),  // 우하
            Position(-2, -1) to Position(-1, 0), // 좌상
            Position(-2, 1)  to Position(-1, 0), // 좌하
            Position(2, -1)  to Position(1, 0),  // 우상
            Position(2, 1)   to Position(1, 0)   // 우하
        )

        for ((move, myeok) in moves) {
            val myeokPos = this.position + myeok // 멱 위치 확인
            val nextPos = this.position + move // 실제 이동목적지 위치 확인

            if (board.isWithinBounds(myeokPos) && board.getPieceAt(myeokPos) == null) {
                if (board.isWithinBounds(nextPos)) {
                    val pieceAtNextPos = board.getPieceAt(nextPos)
                    if (pieceAtNextPos == null || pieceAtNextPos.team != this.team) { movablePosition.add(nextPos) }
                }
            }
        }

        return movablePosition
    }
}
