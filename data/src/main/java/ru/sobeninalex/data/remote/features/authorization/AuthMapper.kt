package ru.sobeninalex.data.remote.features.authorization

import ru.sobeninalex.utils.preferences.user_prefs.UserSettings
import ru.sobeninalex.data.remote.features.authorization.dto.AuthDataDTO
import ru.sobeninalex.domain.features.authorization.model.AuthResult
import ru.sobeninalex.utils.helpers.toCurrentUrl

fun AuthDataDTO.toAuthResult(): AuthResult {
    return AuthResult(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar?.toCurrentUrl(),
        token = token,
        followersCount = followersCount,
        followingCount = followingCount,
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