package com.example.kmp_social_app.feature.post.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommentDTO(
    @SerialName("comment_id") val commentId: String,
    @SerialName("post_id") val postId: String,
    @SerialName("user_id") val userId: String,
    @SerialName("content") val content: String,
    @SerialName("user_name") val userName: String,
    @SerialName("avatar") val avatar: String?,
    @SerialName("created_at") val createdAt: String,
)
