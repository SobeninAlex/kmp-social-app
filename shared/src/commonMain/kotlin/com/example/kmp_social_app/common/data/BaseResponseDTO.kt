package com.example.kmp_social_app.common.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BaseResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("error_message") val errorMessage: String? = null,
)
