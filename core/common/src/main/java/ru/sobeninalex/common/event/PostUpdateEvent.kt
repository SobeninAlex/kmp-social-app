package ru.sobeninalex.common.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.sobeninalex.domain.features.post.model.Post

object PostUpdateEvent {
    private val _event = Channel<Post>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(post: Post) {
        _event.send(post)
    }
}