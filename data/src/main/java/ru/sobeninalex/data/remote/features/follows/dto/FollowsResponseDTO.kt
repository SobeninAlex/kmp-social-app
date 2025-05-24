package ru.sobeninalex.data.remote.features.follows.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowsResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("follows") val follows: List<FollowUserDTO> = emptyList(),
    @SerialName("error_message") val errorMessage: String? = null
)