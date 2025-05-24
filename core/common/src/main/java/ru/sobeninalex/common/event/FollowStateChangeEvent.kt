package ru.sobeninalex.common.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object FollowStateChangeEvent {
    private val _event = Channel<Unit>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent() {
        _event.send(Unit)
    }
}