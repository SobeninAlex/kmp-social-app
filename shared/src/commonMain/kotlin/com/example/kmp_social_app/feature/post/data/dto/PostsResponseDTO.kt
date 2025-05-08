package com.example.kmp_social_app.feature.post.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostsResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("posts") val posts: List<PostDTO> = emptyList(),
    @SerialName("error_message") val errorMessage: String? = null,
)
