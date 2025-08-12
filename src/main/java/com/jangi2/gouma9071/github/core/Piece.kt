package com.jangi2.gouma9071.github.core

abstract class Piece(val team: team, open var position: Position) {
    abstract val score: Float
    abstract val piecetype: PieceType
    abstract fun getMovablePositions(board: Board): List<Position>
}

