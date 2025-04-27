package com.example.kmp_social_app.auth.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SingUpRequestDTO(
    @SerialName("user_name") val name: String,
    @SerialName("user_email") val email: String,
    @SerialName("user_password") val password: String,
)