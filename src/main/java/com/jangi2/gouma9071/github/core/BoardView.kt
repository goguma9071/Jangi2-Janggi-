package com.jangi2.gouma9071.github.core

import javafx.scene.effect.DropShadow
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import java.lang.Exception

/**
 * 오직 장기판과 기물을 그리는 역할만 담당하는 순수 View.
 * Board 데이터를 받아서 화면에 표시하고, 화면 클릭 이벤트를 상위 컨트롤러에 전달한다.
 */
class BoardView(private var gameState: GameState) : Pane() {

    // 외부 컨트롤러에 이동 요청을 알리는 콜백 함수
    var onMoveRequested: ((MoveAction) -> Unit)? = null

    private val pieceImages = mutableMapOf<PieceType, Image>()
    private var selectedPosition: Position? = null
    private val highlight = ImageView()

    companion object {
        // 장기판 이미지의 실제 픽셀 사이즈와 장기판 좌표(9x10)를 기반으로 계산해야 함
        // 이 값들은 실제 장기판 이미지에 맞게 미세 조정이 필요함
        const val CELL_WIDTH = 55.5
        const val CELL_HEIGHT = 55.5
        const val BOARD_OFFSET_X = 45.0
        const val BOARD_OFFSET_Y = 45.0
    }

    init {
        loadPieceImages()
        setupHighlight()
        drawBackground()
        drawPieces()
        setupMouseClickHandler()
    }

    private fun setupHighlight() {
        // TODO: 더 나은 하이라이트 효과 (예: 별도 이미지 사용)
        highlight.effect = DropShadow(20.0, Color.YELLOW)
        highlight.isVisible = false
    }

    private fun drawBackground() {
        val boardImage = Image(javaClass.getResourceAsStream("/images/장기판.png"))
        val boardImageView = ImageView(boardImage)
        boardImageView.fitWidth = 600.0
        boardImageView.fitHeight = 650.0
        children.add(boardImageView)
    }

    private fun loadPieceImages() {
        PieceType.values().forEach { pieceType ->
            val imagePath = "/images/${pieceType.name}.png"
            try {
                val image = Image(javaClass.getResourceAsStream(imagePath))
                pieceImages[pieceType] = image
            } catch (e: Exception) {
                println("이미지 로딩 실패: $imagePath")
            }
        }
    }

    private fun drawPieces() {
        // 기물 ImageView만 지우고 다시 그림 (배경, 하이라이트는 유지)
        children.removeIf { it.id == "piece" }

        gameState.board.getAllPieces().forEach { piece ->
            val image = pieceImages[piece.pieceType]
            if (image != null) {
                val imageView = ImageView(image)
                imageView.id = "piece" // 식별자 추가
                imageView.fitWidth = CELL_WIDTH
                imageView.fitHeight = CELL_HEIGHT

                imageView.x = (piece.position.x * CELL_WIDTH) + BOARD_OFFSET_X - (CELL_WIDTH / 2)
                imageView.y = (piece.position.y * CELL_HEIGHT) + BOARD_OFFSET_Y - (CELL_HEIGHT / 2)

                children.add(imageView)
            }
        }
        // 하이라이트가 항상 맨 위에 오도록 설정
        children.remove(highlight)
        children.add(highlight)
    }

    private fun setupMouseClickHandler() {
        setOnMouseClicked { event ->
            val clickedPos = Position(
                ((event.x - BOARD_OFFSET_X + CELL_WIDTH / 2) / CELL_WIDTH).toInt(),
                ((event.y - BOARD_OFFSET_Y + CELL_HEIGHT / 2) / CELL_HEIGHT).toInt()
            )

            if (!gameState.board.isWithinBounds(clickedPos)) return@setOnMouseClicked

            val pieceAtClickedPos = gameState.board.getPieceAt(clickedPos)

            if (selectedPosition == null) {
                // 선택된 기물이 없을 때
                if (pieceAtClickedPos != null && pieceAtClickedPos.team == gameState.currentTeam) {
                    selectedPosition = clickedPos
                    highlight.image = pieceImages[pieceAtClickedPos.pieceType]
                    highlight.x = (clickedPos.x * CELL_WIDTH) + BOARD_OFFSET_X - (CELL_WIDTH / 2)
                    highlight.y = (clickedPos.y * CELL_HEIGHT) + BOARD_OFFSET_Y - (CELL_HEIGHT / 2)
                    highlight.isVisible = true
                }
            } else {
                // 기물이 이미 선택된 상태일 때
                if (selectedPosition == clickedPos) {
                    // 같은 위치 재클릭: 선택 취소
                    selectedPosition = null
                    highlight.isVisible = false
                } else {
                    // 다른 위치 클릭: 이동 요청
                    val moveAction = MoveAction(selectedPosition!!, clickedPos)
                    onMoveRequested?.invoke(moveAction) // 컨트롤러에 이동 요청
                    selectedPosition = null
                    highlight.isVisible = false
                }
            }
        }
    }

    fun update(newGameState: GameState) {
        this.gameState = newGameState
        // 선택 하이라이트가 새 상태와 맞지 않을 수 있으므로 숨김
        highlight.isVisible = false
        selectedPosition = null
        drawPieces()
    }
}
