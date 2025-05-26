package com.example.kmp_social_app.glue.mappers

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.data.remote.services.post.dto.PostDTO
import ru.sobeninalex.utils.helpers.DateFormatter
import ru.sobeninalex.utils.helpers.toCurrentUrl

fun PostDTO.toPost() = Post(
    postId = postId,
    caption = caption,
    imageUrl = imageUrl?.toCurrentUrl(),
    likesCount = likesCount,
    commentsCount = commentsCount,
    createdAt = DateFormatter.parseDate(createdAt),
    userId = userId,
    userName = userName,
    userAvatar = userAvatar?.toCurrentUrl(),
    isLiked = isLiked,
    isOwnPost = isOwnPost
)

