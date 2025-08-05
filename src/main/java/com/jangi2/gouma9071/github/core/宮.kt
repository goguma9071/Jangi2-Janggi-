package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.core.team

class 宮(team: team, override var position: Position) : Piece(team, position) {
    override val score = PieceScore.宮.score
    override fun getMovablePositions(board: Board): List<Position> {
        // TODO: Implement actual move logic
        return emptyList()
    }
}
