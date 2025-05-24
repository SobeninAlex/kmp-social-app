package ru.sobeninalex.data.remote.features.authorization.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AuthResponseDTO(
    @SerialName("auth_data") val authData: AuthDataDTO? = null,
    @SerialName("error_message") val errorMessage: String? = null
)