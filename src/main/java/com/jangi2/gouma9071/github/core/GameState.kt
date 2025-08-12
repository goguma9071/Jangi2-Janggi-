package com.jangi2.gouma9071.github.core

data class GameState(val board: Board, val currentTeam: team, val pieceCount: Map<PieceType, Byte>, val score: Map<team, Float>) {
}