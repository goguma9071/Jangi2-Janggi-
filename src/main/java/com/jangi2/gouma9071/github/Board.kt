package com.jangi2.gouma9071.github

import com.jangi2.gouma9071.github.Board.Companion.HEIGHT
import com.jangi2.gouma9071.github.Board.Companion.WIDTH

class Board {
    companion object {
        const val WIDTH = 9
        const val HEIGHT = 10
    }

    private val grid: Array<Array<Piece?>>

    init {
        grid = Array(HEIGHT) { Array(WIDTH) { null } }
        resetBoard()
    }

    fun resetBoard() {
        for (y in 0 until HEIGHT) {
            for (x in 0 until WIDTH) {
                grid[y][x] = null
            }
        }
    }

    fun getPieceAt(position: Position): Piece? {
        if (!isWithinBounds(position)) {
            return null
        }
        return grid[position.y][position.x]
    }

    fun isWithinBounds(position: Position): Boolean {
        return position.x >= 0 && position.x < WIDTH && position.y >= 0 && position.y < HEIGHT
    }
}