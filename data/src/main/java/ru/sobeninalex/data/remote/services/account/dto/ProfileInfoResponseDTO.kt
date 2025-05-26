package ru.sobeninalex.data.remote.services.account.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileInfoResponseDTO(
    @SerialName("is_success") val isSuccess: Boolean,
    @SerialName("user") val user: ProfileDTO? = null,
    @SerialName("error_message") val errorMessage: String? = null,
)