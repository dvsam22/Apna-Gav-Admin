package com.example.apnagavadmin.util

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
}

object GlobalEventBus {
    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 64)
    val events = _events.asSharedFlow()

    fun showToast(message: String) {
        _events.tryEmit(UiEvent.ShowToast(message))
    }
}
