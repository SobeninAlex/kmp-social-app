package com.example.kmp_social_app.android.common.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object UnauthorizedEvent {
    private val _event = Channel<Unit>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent() {
        _event.send(Unit)
    }
}