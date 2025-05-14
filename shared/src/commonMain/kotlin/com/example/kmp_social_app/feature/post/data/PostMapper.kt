package com.example.kmp_social_app.feature.post.data

import com.example.kmp_social_app.common.utils.DateFormatter
import com.example.kmp_social_app.common.utils.toCurrentUrl
import com.example.kmp_social_app.feature.post.data.dto.PostCommentDTO
import com.example.kmp_social_app.feature.post.data.dto.PostDTO
import com.example.kmp_social_app.feature.post.domain.model.Post
import com.example.kmp_social_app.feature.post.domain.model.PostComment

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

internal fun PostCommentDTO.toPostComment() = PostComment(
    commentId = commentId,
    postId = postId,
    userId = userId,
    content = content,
    userName = userName,
    avatar = avatar?.toCurrentUrl(),
    createdAt = DateFormatter.parseDate(createdAt),
)