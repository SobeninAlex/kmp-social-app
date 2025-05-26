package ru.sobeninalex.common.event

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.sobeninalex.common.models.profile.Profile

object ProfileUpdateEvent {
    private val _event = MutableSharedFlow<Profile>(replay = 1)
    val event = _event.asSharedFlow()

    suspend fun sendEvent(profile: Profile) {
        _event.emit(profile)
    }
}