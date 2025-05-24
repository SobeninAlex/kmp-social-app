package ru.sobeninalex.data.remote.features.post.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("post") val post: PostDTO? = null,
    @SerialName("error_message") val errorMessage: String? = null,
)
