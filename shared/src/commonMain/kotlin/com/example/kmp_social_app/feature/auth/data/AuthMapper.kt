package com.example.kmp_social_app.feature.auth.data

import com.example.kmp_social_app.common.utils.Constants.toCurrentUrl
import com.example.kmp_social_app.feature.auth.data.dto.AuthDataDTO
import com.example.kmp_social_app.feature.auth.domain.model.AuthResult

internal fun AuthDataDTO.toAuthResult(): AuthResult {
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