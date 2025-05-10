package com.example.kmp_social_app.feature.post.data

import com.example.kmp_social_app.common.utils.Constants.toCurrentUrl
import com.example.kmp_social_app.common.utils.DateFormatter
import com.example.kmp_social_app.feature.post.data.dto.PostDTO
import com.example.kmp_social_app.feature.post.domain.model.Post

internal fun PostDTO.toPost() = Post(
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