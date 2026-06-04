package com.example.ejemplo.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object PlayerState {
    private val _showFullPlayer = MutableStateFlow(false)
    val showFullPlayer: StateFlow<Boolean> = _showFullPlayer

    fun setShowFullPlayer(show: Boolean) {
        _showFullPlayer.value = show
    }
}
