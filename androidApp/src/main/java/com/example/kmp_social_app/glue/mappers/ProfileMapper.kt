package com.example.kmp_social_app.glue.mappers

import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.data.remote.services.account.dto.ProfileDTO
import ru.sobeninalex.utils.helpers.toClientUrl
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings

fun ProfileDTO.toProfile() = Profile(
    id = id,
    name = name,
    email = email,
    bio = bio,
    avatar = avatar?.toClientUrl(),
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = isFollowing ?: false,
    isOwnProfile = isOwnProfile ?: false,
)

fun Profile.toProfileDTO() = ProfileDTO(
    id = id,
    name = name,
    email = email,
    bio = bio,
    avatar = avatar?.toClientUrl(),
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = isFollowing ?: false,
    isOwnProfile = isOwnProfile ?: false,
)

fun Profile.toUserSettings(token: String): UserSettings {
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

fun ProfileDTO.toUserSettings(token: String): UserSettings {
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