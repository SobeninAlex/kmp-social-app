package com.example.kmp_social_app.common.data.local

import com.example.kmp_social_app.feature.auth.domain.model.AuthResult
import kotlinx.serialization.Serializable

@Serializable
data class UserSettings(
    val id: String = "",
    val name: String = "",
    val bio: String = "",
    val avatar: String? = null,
    val token: String = "",
    val followersCount: Int = 0,
    val followingCount: Int = 0,
)

fun UserSettings.toAuthResult(): AuthResult {
    return AuthResult(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount
    )
}

fun AuthResult.toUserSettings(): UserSettings {
    return UserSettings(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount
    )
}