package com.jangi2.gouma9071.github.core


data class Position(val x: Int, val y: Int) {

    operator fun plus(pos: Position): Position {
        val newPosX = this.x + pos.x
        val newPosY = this.y + pos.y

        return Position(newPosX , newPosY)
    }
}




