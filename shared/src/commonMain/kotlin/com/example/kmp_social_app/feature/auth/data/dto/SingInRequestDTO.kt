package com.example.kmp_social_app.feature.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SingInRequestDTO(
    @SerialName("user_email") val email: String,
    @SerialName("user_password") val password: String,
)