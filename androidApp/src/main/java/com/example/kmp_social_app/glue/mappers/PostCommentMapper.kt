package com.example.kmp_social_app.glue.mappers

import ru.sobeninalex.common.models.post.PostComment
import ru.sobeninalex.data.remote.services.post.dto.PostCommentDTO
import ru.sobeninalex.utils.helpers.DateFormatter
import ru.sobeninalex.utils.helpers.toClientUrl

fun PostCommentDTO.toPostComment(isOwnComment: Boolean) = PostComment(
    commentId = commentId,
    postId = postId,
    userId = userId,
    content = content,
    userName = userName,
    avatar = avatar?.toClientUrl(),
    createdAt = DateFormatter.parseDate(createdAt),
    isOwnComment = isOwnComment
)