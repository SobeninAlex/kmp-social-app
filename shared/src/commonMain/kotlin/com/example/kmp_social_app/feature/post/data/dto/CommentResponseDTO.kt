package com.example.kmp_social_app.feature.post.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CommentResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("post_comment") val postComment: PostCommentDTO? = null,
    @SerialName("error_message") val errorMessage: String? = null,
)
