package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.core.team

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
        // 1. 보드 초기화
        for (y in 0 until HEIGHT) {
            for (x in 0 until WIDTH) {
                grid[y][x] = null
            }
        }

        // 2. 기물 배치 (초나라 - 상단)
        val choTeam = team.楚 // 초나라 팀
        grid[0][0] = 車(choTeam, Position(0, 0))
        grid[0][1] = 馬(choTeam, Position(1, 0))
        grid[0][2] = 象(choTeam, Position(2, 0))
        grid[0][3] = 士(choTeam, Position(3, 0))
        grid[1][4] = 宮(choTeam, Position(4, 1)) // 궁은 중앙에 위치
        grid[0][5] = 士(choTeam, Position(5, 0))
        grid[0][6] = 象(choTeam, Position(6, 0))
        grid[0][7] = 馬(choTeam, Position(7, 0))
        grid[0][8] = 車(choTeam, Position(8, 0))

        grid[2][1] = 包(choTeam, Position(1, 2))
        grid[2][7] = 包(choTeam, Position(7, 2))

        grid[3][0] = 卒(choTeam, Position(0, 3))
        grid[3][2] = 卒(choTeam, Position(2, 3))
        grid[3][4] = 卒(choTeam, Position(4, 3))
        grid[3][6] = 卒(choTeam, Position(6, 3))
        grid[3][8] = 卒(choTeam, Position(8, 3))


        // 3. 기물 배치 (한나라 - 하단)
        val hanTeam = team.漢 // 한나라 팀
        grid[9][0] = 車(hanTeam, Position(0, 9))
        grid[9][1] = 馬(hanTeam, Position(1, 9))
        grid[9][2] = 象(hanTeam, Position(2, 9))
        grid[9][3] = 士(hanTeam, Position(3, 9))
        grid[8][4] = 宮(hanTeam, Position(4, 8)) // 궁은 중앙에 위치
        grid[9][5] = 士(hanTeam, Position(5, 9))
        grid[9][6] = 象(hanTeam, Position(6, 9))
        grid[9][7] = 馬(hanTeam, Position(7, 9))
        grid[9][8] = 車(hanTeam, Position(8, 9))

        grid[7][1] = 包(hanTeam, Position(1, 7))
        grid[7][7] = 包(hanTeam, Position(7, 7))

        grid[6][0] = 卒(hanTeam, Position(0, 6))
        grid[6][2] = 卒(hanTeam, Position(2, 6))
        grid[6][4] = 卒(hanTeam, Position(4, 6))
        grid[6][6] = 卒(hanTeam, Position(6, 6))
        grid[6][8] = 卒(hanTeam, Position(8, 6))
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

    fun movePiece(from: Position, to: Position) {
        if (!isWithinBounds(from) || !isWithinBounds(to)) return

        val piece = getPieceAt(from)
        if (piece != null) {
            grid[to.y][to.x] = piece
            piece.position = to
            grid[from.y][from.x] = null
        }
    }
}