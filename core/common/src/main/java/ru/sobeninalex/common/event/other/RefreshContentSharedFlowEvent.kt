package ru.sobeninalex.common.event.other

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object RefreshContentSharedFlowEvent {
    private val _event = MutableSharedFlow<Unit>()
    val event = _event.asSharedFlow()

    suspend fun sendEvent() {
        _event.emit(Unit)
    }
}