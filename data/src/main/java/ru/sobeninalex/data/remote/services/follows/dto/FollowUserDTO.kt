package ru.sobeninalex.data.remote.services.follows.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FollowUserDTO(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("bio") val bio: String,
    @SerialName("avatar") val avatar: String?,
    @SerialName("is_following") val isFollowing: Boolean
)