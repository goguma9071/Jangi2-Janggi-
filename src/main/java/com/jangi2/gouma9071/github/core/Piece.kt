package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.core.team

abstract class Piece(val team: team, open var position: Position) {
    abstract fun getMovablePositions(board: Board): List<Position>
}
