package com.example.kmp_social_app.feature.follows.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FollowsRequestDTO(
    @SerialName("follower") val follower: String,
    @SerialName("following") val following: String,
)