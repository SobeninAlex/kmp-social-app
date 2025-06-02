package com.example.kmp_social_app.glue.mappers

import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.data.remote.services.follows.dto.FollowUserDTO
import ru.sobeninalex.utils.helpers.toClientUrl

fun FollowUserDTO.toFollowUser() = FollowUser(
    id = id,
    name = name,
    bio = bio,
    avatar = avatar?.toClientUrl(),
    isFollowing = isFollowing
)