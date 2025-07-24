package com.jangi2.gouma9071.github.internet

import com.jangi2.gouma9071.github.core.Position

interface P2PEventListener {
    fun onConnected()
    fun onMoveReceived(from: Position, to: Position)
    fun onDisconnected()
    fun onError(message: String)
}