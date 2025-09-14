package com.jangi2.gouma9071.github.core

enum class team {
        楚, 漢;
        fun opposite(): team {
                return when (this) {
                        楚 -> 漢
                        漢 -> 楚
                }
        }
}

