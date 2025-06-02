package ru.sobeninalex.common.event.post

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.sobeninalex.common.models.post.Post

object PostUpdatedChannelEvent {
    private val _event = Channel<Post>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(post: Post) {
        _event.send(post)
    }
}