package ru.sobeninalex.data.remote.services.authorization.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SingUpRequestDTO(
    @SerialName("user_name") val name: String,
    @SerialName("user_email") val email: String,
    @SerialName("user_password") val password: String,
)