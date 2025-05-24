package ru.sobeninalex.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class UpdatedEntityEvent<T> {
    private val _event = Channel<T>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(entity: T) {
        _event.send(entity)
    }
}