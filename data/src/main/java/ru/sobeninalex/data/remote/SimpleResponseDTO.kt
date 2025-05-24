package ru.sobeninalex.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("error_message") val errorMessage: String? = null,
)
