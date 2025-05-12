package com.example.kmp_social_app.feature.account.data

import com.example.kmp_social_app.common.data.local.UserSettings
import com.example.kmp_social_app.feature.account.data.dto.ProfileDTO
import com.example.kmp_social_app.feature.account.domain.model.Profile

internal fun ProfileDTO.toProfile() = Profile(
    id = id,
    name = name,
    email = email,
    bio = bio,
    avatar = avatar,
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = isFollowing ?: false,
    isOwnProfile = isOwnProfile ?: false,
)

internal fun UserSettings.toProfile() = Profile(
    id = id,
    name = name,
    email = "",
    bio = bio,
    avatar = avatar,
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = false,
    isOwnProfile = true
)

internal fun Profile.toUserSettings(token: String): UserSettings {
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