package com.jangi2.gouma9071.github.core

abstract class Piece(open val team: team, open val position: Position) {
    abstract val score: Float
    abstract val pieceType: PieceType
    abstract fun getMovablePositions(board: Board): List<Position>
}

