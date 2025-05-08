package com.example.kmp_social_app.common.utils

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SimpleResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("error_message") val errorMessage: String? = null,
)
