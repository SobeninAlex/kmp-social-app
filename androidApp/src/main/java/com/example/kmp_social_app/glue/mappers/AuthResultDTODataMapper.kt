package com.example.kmp_social_app.glue.mappers

import ru.sobeninalex.common.models.auth.AuthResult
import ru.sobeninalex.data.remote.services.authorization.dto.AuthResultDTO
import ru.sobeninalex.utils.helpers.toCurrentUrl
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings

fun AuthResultDTO.toAuthResult(): AuthResult {
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