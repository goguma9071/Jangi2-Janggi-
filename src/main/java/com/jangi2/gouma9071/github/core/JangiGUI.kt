package com.jangi2.gouma9071.github.core

import java.awt.Graphics
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

class JangiGUI : JFrame() {

    private val board: Board = Board()
    private val boardImage = ImageIO.read(File("src/main/resources/images/장기판.jpg"))

    init {
        title = "Jangi Game"
        setSize(800, 900)
        defaultCloseOperation = EXIT_ON_CLOSE
        val boardPanel = BoardPanel()
        add(boardPanel)
    }

    inner class BoardPanel : JPanel() {
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            g.drawImage(boardImage, 0, 0, width, height, this)
            drawPieces(g)
        }

        private fun drawPieces(g: Graphics) {
            for (y in 0 until Board.HEIGHT) {
                for (x in 0 until Board.WIDTH) {
                    val piece = board.getPieceAt(Position(x, y))
                    if (piece != null) {
                        drawPiece(g, piece)
                    }
                }
            }
        }

        private fun drawPiece(g: Graphics, piece: Piece) {
            val imagePath = "src/main/resources/images/${getPieceImageName(piece)}.png"
            try {
                val pieceImage = ImageIO.read(File(imagePath))
                val panelX = piece.position.x * (width / Board.WIDTH)
                val panelY = piece.position.y * (height / Board.HEIGHT)
                g.drawImage(pieceImage, panelX, panelY, 50, 50, this)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun getPieceImageName(piece: Piece): String {
            return when (piece) {
                is 車 -> "車"
                is 馬 -> "馬"
                is 象 -> "象"
                is 士 -> "士"
                is 宮 -> "宮"
                is 包 -> "包"
                is 卒 -> "卒"
                else -> ""
            }
        }
    }
}


fun main() {
    SwingUtilities.invokeLater { JangiGUI().isVisible = true }
}
