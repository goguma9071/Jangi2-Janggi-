package com.jangi2.gouma9071.github

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.Pane
import javafx.stage.Stage

class Jangi : Application() {
    override fun start(primaryStage: Stage) {
        val root = Pane()

        // 장기판 이미지 로드 및 표시
        val boardImage = Image(javaClass.getResourceAsStream("/images/장기판.jpg"))
        val boardImageView = ImageView(boardImage)
        boardImageView.fitWidth = 800.0
        boardImageView.fitHeight = 600.0
        root.children.add(boardImageView)

        // 궁 이미지 로드 및 표시
        val gungImage = Image(javaClass.getResourceAsStream("/images/宮.png"))
        val gungImageView = ImageView(gungImage)
        gungImageView.x = 350.0 // X 좌표 (임의 설정)
        gungImageView.y = 250.0 // Y 좌표 (임의 설정)
        gungImageView.fitWidth = 100.0
        gungImageView.fitHeight = 100.0
        root.children.add(gungImageView)

        val scene = Scene(root, 800.0, 600.0)
        primaryStage.title = "Jangi Game"
        primaryStage.scene = scene
        primaryStage.show()
    }
}

fun main() {
    Application.launch(Jangi::class.java)
}