package com.jangi2.gouma9071.github

import com.jangi2.gouma9071.github.team.team

class 包(team: team, override var position: Position) : Piece(team, position) {
    override fun getMovablePositions(board: Board): List<Position> {
        // TODO: Implement actual move logic
        return emptyList()
    }
}
