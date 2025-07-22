package com.jangi2.gouma9071.github

import com.jangi2.gouma9071.github.team.team

abstract class Piece(val team: team, open var position: Position) {
    abstract fun getMovablePositions(board: Board): List<Position>
}
