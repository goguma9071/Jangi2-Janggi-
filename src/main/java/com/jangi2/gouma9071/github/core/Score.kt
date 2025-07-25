package com.jangi2.gouma9071.github.core


data class Score(
    var HanScore: Float , var ChoScore: Float ) {

    operator fun minus(score: Score): Score {
        // 점수 계산 로직 구현 할 것
        return Score()
    }
}
