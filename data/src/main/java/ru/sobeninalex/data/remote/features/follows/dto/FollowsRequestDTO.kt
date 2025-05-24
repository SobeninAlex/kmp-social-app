package ru.sobeninalex.data.remote.features.follows.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowsRequestDTO(
    @SerialName("follower") val follower: String,
    @SerialName("following") val following: String,
)