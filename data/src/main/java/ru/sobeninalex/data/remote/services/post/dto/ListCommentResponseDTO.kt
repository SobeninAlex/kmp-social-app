package ru.sobeninalex.data.remote.services.post.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ListCommentResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("post_comments") val postComments: List<PostCommentDTO> = emptyList(),
    @SerialName("error_message") val errorMessage: String? = null,
)
