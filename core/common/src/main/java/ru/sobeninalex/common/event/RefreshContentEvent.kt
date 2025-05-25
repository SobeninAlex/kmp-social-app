package ru.sobeninalex.common.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object RefreshContentEvent {
    private val _event = MutableSharedFlow<Unit>(replay = 1)
    val event = _event.asSharedFlow()

    suspend fun sendEvent() {
        _event.emit(Unit)
    }
}