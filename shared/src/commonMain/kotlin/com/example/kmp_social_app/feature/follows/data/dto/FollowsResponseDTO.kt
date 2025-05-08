package com.example.kmp_social_app.feature.follows.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FollowsResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("follows") val follows: List<FollowUserDTO> = emptyList(),
    @SerialName("error_message") val errorMessage: String? = null
)