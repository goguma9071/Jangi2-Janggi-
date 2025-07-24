package com.jangi2.gouma9071.github.core

import java.awt.event.MouseEvent
import javax.swing.JPanel

class MouseHandler(private val jangi: Jangi, private val board: Board, private val panel: JPanel) {

    private var selectPiece: Piece? = null

    fun mousePressed(e: MouseEvent) {
        //1. 픽셀좌표를 장기판 좌표로 변환
        val cellWidth = panel.width / Board.WIDTH
        val cellHeight = panel.height / Board.HEIGHT
        val clikedPos = Position(e.x / cellWidth, e.y / cellHeight)

        //2. 상태 별 로직 분리
        if (selectPiece == null) {
            val piece = board.getPieceAt(clikedPos)
            if (piece != null) {
                if  (piece.team == jangi.currentTurn) {
                    selectPiece = piece
                    //선택된 기물 테두리 그리는 기능 구현 할 것
                }
            }
        } else {
            // 기물이 이미 선택된 상태에서 다른 곳을 클릭했을 때의 로직
        // (이동 또는 선택 취소)
        // ...
        // 이동에 성공했다면 jangi.switchTurn() 호출!

        }
    }




}