package com.example.kmp_social_app.android.common.utils.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object SnackbarEvent {
    private val _event = Channel<Event>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(event: Event) {
        _event.send(event)
    }

    data class Event(
        val message: String,
        val action: Action? = null
    )

    data class Action(
        val buttonName: String,
        val action: suspend () -> Unit
    )
}