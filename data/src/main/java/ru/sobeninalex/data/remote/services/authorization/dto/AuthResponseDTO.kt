package ru.sobeninalex.data.remote.services.authorization.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponseDTO(
    @SerialName("auth_data") val authData: AuthResultDTO? = null,
    @SerialName("error_message") val errorMessage: String? = null
)