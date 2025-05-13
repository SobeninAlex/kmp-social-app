package com.example.kmp_social_app.android.common.utils.event

import com.example.kmp_social_app.feature.post.domain.model.Post
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

object PostUpdatedEvent {
    private val _event = Channel<Post>()
    val event = _event.receiveAsFlow()

    suspend fun sendEvent(post: Post) {
        _event.send(post)
    }
}