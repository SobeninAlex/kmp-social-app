package com.example.kmp_social_app.feature.follows.data

import com.example.kmp_social_app.common.utils.toCurrentUrl
import com.example.kmp_social_app.feature.follows.data.dto.FollowUserDTO
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser

internal fun FollowUserDTO.toFollowUser() = FollowUser(
    id = id,
    name = name,
    bio = bio,
    avatar = avatar?.toCurrentUrl(),
    isFollowing = isFollowing
)