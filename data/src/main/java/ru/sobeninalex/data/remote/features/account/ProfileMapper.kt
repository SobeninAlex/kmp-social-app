package ru.sobeninalex.data.remote.features.account

import ru.sobeninalex.utils.preferences.user_prefs.UserSettings
import ru.sobeninalex.data.remote.features.account.dto.ProfileDTO
import ru.sobeninalex.domain.features.account.model.Profile
import ru.sobeninalex.utils.helpers.toCurrentUrl

fun ProfileDTO.toProfile() = Profile(
    id = id,
    name = name,
    email = email,
    bio = bio,
    avatar = avatar?.toCurrentUrl(),
    followersCount = followersCount,
    followingCount = followingCount,
    isFollowing = isFollowing ?: false,
    isOwnProfile = isOwnProfile ?: false,
)

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