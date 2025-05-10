package com.example.kmp_social_app.android.common.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val message: String,
    val snackbarAction: SnackbarAction? = null
)

data class SnackbarAction(
    val buttonName: String,
    val action: suspend () -> Unit
)

object SnackbarController {
    private val _event = Channel<SnackbarEvent>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _event.send(event)
    }
}