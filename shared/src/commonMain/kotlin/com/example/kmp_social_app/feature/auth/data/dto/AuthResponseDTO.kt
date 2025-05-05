package com.example.kmp_social_app.feature.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AuthResponseDTO(
    @SerialName("auth_data") val authData: AuthDataDTO? = null,
    @SerialName("error_message") val errorMessage: String? = null
)