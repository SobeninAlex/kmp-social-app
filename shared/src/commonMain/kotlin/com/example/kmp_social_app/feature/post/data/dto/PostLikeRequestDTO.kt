package com.example.kmp_social_app.feature.post.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostLikeRequestDTO(
    @SerialName("post_id") val postId: String,
    @SerialName("user_id") val userId: String
)