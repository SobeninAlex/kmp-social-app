package ru.sobeninalex.data.remote.features.post.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostsResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("posts") val posts: List<PostDTO> = emptyList(),
    @SerialName("error_message") val errorMessage: String? = null,
)
