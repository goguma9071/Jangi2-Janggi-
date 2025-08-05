package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.core.Board

data class Score(
    var Score: Float , val team: team) {

    var HanScore = 73.5f
    var ChoScore = 72f

    fun minusScore(capturedPiece: Piece) {
        if (capturedPiece.team == com.jangi2.gouma9071.github.core.team.楚) {
            val newChoScore = ChoScore - capturedPiece.score
            ChoScore = newChoScore
        } else {
            val newHanScore = HanScore - capturedPiece.score
            HanScore = newHanScore
        }
    }
    fun reset() {
        HanScore = 73.5f
        ChoScore = 72f
    }
}
