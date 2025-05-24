package ru.sobeninalex.data.remote.features.follows

import ru.sobeninalex.data.remote.features.follows.dto.FollowUserDTO
import ru.sobeninalex.domain.features.follows.model.FollowUser
import ru.sobeninalex.utils.helpers.toCurrentUrl

fun FollowUserDTO.toFollowUser() = FollowUser(
    id = id,
    name = name,
    bio = bio,
    avatar = avatar?.toCurrentUrl(),
    isFollowing = isFollowing
)