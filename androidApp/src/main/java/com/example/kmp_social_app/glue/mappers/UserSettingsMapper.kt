package com.example.kmp_social_app.glue.mappers

import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.data.remote.services.account.dto.ProfileDTO
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings

fun UserSettings.toProfile() = Profile(
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

fun UserSettings.toProfileDTO() = ProfileDTO(
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