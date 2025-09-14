package com.jangi2.gouma9071.github.core

data class GameState(val board: Board, val currentTeam: team, val pieceCount: Map<team, Map<PieceType, Byte>>) {
}
/**
 * 게임 상태 저장용
 * 여기서 JangiGame이 정보를 받아옴
 */