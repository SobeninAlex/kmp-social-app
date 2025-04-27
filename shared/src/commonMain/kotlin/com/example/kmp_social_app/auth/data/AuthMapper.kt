package com.example.kmp_social_app.auth.data

import com.example.kmp_social_app.auth.data.dto.AuthDataDTO
import com.example.kmp_social_app.auth.domain.model.AuthResult

internal fun AuthDataDTO.toAuthResult(): AuthResult {
    return AuthResult(
        id = id,
        name = name,
        bio = bio,
        avatar = avatar,
        token = token,
        followersCount = followersCount,
        followingCount = followingCount,
    )
}