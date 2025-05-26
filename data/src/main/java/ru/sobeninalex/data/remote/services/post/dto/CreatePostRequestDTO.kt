package ru.sobeninalex.data.remote.services.post.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequestDTO(
    @SerialName("user_id") val userId: String,
    @SerialName("caption") val caption: String
)