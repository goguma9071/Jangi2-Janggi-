package com.jangi2.gouma9071.github.core

import com.jangi2.gouma9071.github.core._
import scala.jdk.CollectionConverters._
import com.jangi2.gouma9071.github.core.Position


class JangiGame {
  /** 코드 효율성 위해 스칼라로 작성
   * 로직만 스칼라에서 처리 후 코틀린 GameState로 반환 */
  def applyMove(gameState: GameState, moveAction: MoveAction): GameState ={
    val board = gameState.getBoard()
    val currentTeam = gameState.getCurrentTeam()
    val from = moveAction.getFrom
    val to = moveAction.getTo
    val pieceToMove = Option(board.getPieceAt(moveAction.getFrom()))

    pieceToMove match {
      case Some(piece) =>
        val movablePositions = piece.getMovablePositions(board).asScala.toList

        if (piece.getTeam == currentTeam && movablePositions.contains(to)) {
          val newBoard= board.copy()
          newBoard.movePiece(from, to)

          val (newPieceCount, newScore) = Score(newBoard)
          val nextTeam = if (gameState.getCurrentTeam == team.楚) team.漢 else team.楚

          gameState.copy(

            newBoard,
            nextTeam,
            newPieceCount,
            newScore
          )

        } else {
          gameState
        }
      case None => gameState
    }

  }

  private def Score(board: Board): (java.util.Map[team, java.util.Map[PieceType, java.lang.Byte]], java.util.Map[team, java.lang.Float]) = {

    val scores = scala.collection.mutable.Map[team, java.lang.Float](team.楚 -> 0f , team.漢 -> 0f)
    val counts = scala.collection.mutable.Map[team, scala.collection.mutable.Map[PieceType, java.lang.Byte]](
      team.楚 -> scala.collection.mutable.Map[PieceType, java.lang.Byte]() ,
      team.漢 -> scala.collection.mutable.Map[PieceType, java.lang.Byte]()
    )

    PieceType.values().foreach {
      pieceType =>
        counts(team.楚)(pieceType) = 0.toByte
        counts(team.漢)(pieceType) = 0.toByte
    }

    for (y <- 0 until Board.HEIGHT; x <- 0 until Board.WIDTH) {

      Option(board.getPieceAt(new Position(x, y))).foreach {
        piece =>
          val pieceTeam = piece.getTeam
          val pieceType = piece.getPieceType
          val scoreValue = PieceScore.valueOf(pieceType.name).getScore

          scores(pieceTeam) = scores(pieceTeam) + scoreValue
          counts(pieceTeam)(pieceType) = (counts(pieceTeam)(pieceType) + 1).toByte
      }
    }
    val finalScores = scores.toMap.asJava
    val finalCountsMap = Map(team.楚 -> counts(team.楚).toMap.asJava, team.漢 -> counts(team.漢).toMap.asJava).asJava

    (finalCountsMap, finalScores)
  }
}