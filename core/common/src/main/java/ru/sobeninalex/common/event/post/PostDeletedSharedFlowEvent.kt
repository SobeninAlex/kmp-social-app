package ru.sobeninalex.common.event.post

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object PostDeletedSharedFlowEvent {
    private val _event = MutableSharedFlow<String>(replay = 1)
    val event = _event.asSharedFlow()

    suspend fun sendEvent(postId: String) {
        _event.emit(postId)
    }
}