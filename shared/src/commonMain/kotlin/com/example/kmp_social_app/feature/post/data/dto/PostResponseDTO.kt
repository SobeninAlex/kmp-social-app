package com.example.kmp_social_app.feature.post.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("post") val post: PostDTO? = null,
    @SerialName("error_message") val errorMessage: String? = null,
)
