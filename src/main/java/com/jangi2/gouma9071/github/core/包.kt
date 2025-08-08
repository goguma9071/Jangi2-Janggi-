package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.core.team



class 包(team: team, override var position: Position) : Piece(team, position) {
    override val score = PieceScore.包.score
    override fun getMovablePositions(board: Board): List<Position> {
        val movablePosition = mutableListOf<Position>()
        val directions = mutableSetOf<Position>()

        directions.add(Position(0, 1))
        directions.add(Position(0, -1))
        directions.add(Position(1, 0))
        directions.add(Position(-1, 0))
        var hasFoundBridge = false

        for (dir in directions) {
            var nextPos = this.position + dir
            hasFoundBridge = false
            val pieceAtNextPos = board.getPieceAt(nextPos)

            while (board.isWithinBounds(nextPos)) {

                if (pieceAtNextPos != null) {
                    if (pieceAtNextPos is 包) {
                        break
                    }
                    hasFoundBridge = true
                    nextPos += dir
                    break
                }
                nextPos += dir
            }
            if (hasFoundBridge == true) {
                while (board.isWithinBounds(nextPos)) {
                    if (pieceAtNextPos == null) {
                        movablePosition.add(nextPos)
                    } else {
                        if (pieceAtNextPos !is 包 && pieceAtNextPos.team != this.team) {
                            movablePosition.add(nextPos)
                        }
                        break
                    }
                    nextPos += dir
                }
            }
        }

        if (board.isInsidePalace(this.position)) {
            if (board.getPieceAt(Position(4, 1)) != null && board.getPieceAt(Position(4, 1)) !is 包
                || board.getPieceAt(Position(4, 8)) != null && board.getPieceAt(Position(4, 8)) !is 包) {

                when (this.position.y <=2) {
                    true -> {
                        if (this.position == Position(3, 0)) {
                            val target  = board.getPieceAt(Position(5, 2))
                         if ( target == null || target !is 包 && target.team != this.team) {
                             movablePosition.add(Position(5, 2))
                         }
                        }
                        else if (this.position == Position(5, 2)) {
                            val target  = board.getPieceAt(Position(3, 0))
                            if ( target == null || target !is 包 && target.team != this.team) {
                                movablePosition.add(Position(3, 0))
                            }
                        }
                        else if (this.position == Position(5, 0)) {
                            val target  = board.getPieceAt(Position(3, 2))
                            if ( target == null || target !is 包 && target.team != this.team) {
                                movablePosition.add(Position(3, 2))
                            }
                        }
                        else if (this.position == Position(3, 2)) {
                            val target  = board.getPieceAt(Position(5, 0))
                            if ( target == null || target !is 包 && target.team != this.team) {
                                movablePosition.add(Position(5, 0))
                            }
                        }
                    }
                    else -> {
                        if (this.position == Position(3, 7)) {
                            val target  = board.getPieceAt(Position(5, 9))
                            if ( target == null || target !is 包 && target.team != this.team) {
                                movablePosition.add(Position(5, 9))
                            }
                        }
                        else if (this.position == Position(5, 9)) {
                            val target  = board.getPieceAt(Position(3, 7))
                            if ( target == null || target !is 包 && target.team != this.team) {
                                movablePosition.add(Position(3, 7))
                            }
                        }
                        else if (this.position == Position(5, 7)) {
                            val target  = board.getPieceAt(Position(3, 9))
                            if ( target == null || target !is 包 && target.team != this.team) {
                                movablePosition.add(Position(3, 9))
                            }
                        }
                        else if (this.position == Position(3, 9)) {
                            val target  = board.getPieceAt(Position(5, 7))
                            if ( target == null || target !is 包 && target.team != this.team) {
                                movablePosition.add(Position(5, 7))
                            }
                        }
                    }
                }
            }
        }

        return movablePosition
    }
}
