package ru.sobeninalex.common.event.other

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object UnauthorizedEvent {
    private val _event = Channel<Unit>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent() {
        _event.send(Unit)
    }
}