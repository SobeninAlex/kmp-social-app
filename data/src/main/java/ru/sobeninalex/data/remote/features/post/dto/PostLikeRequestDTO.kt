package ru.sobeninalex.data.remote.features.post.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostLikeRequestDTO(
    @SerialName("post_id") val postId: String,
    @SerialName("user_id") val userId: String
)