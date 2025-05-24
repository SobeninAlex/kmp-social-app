package ru.sobeninalex.data.remote.features.post

import ru.sobeninalex.data.remote.features.post.dto.PostCommentDTO
import ru.sobeninalex.data.remote.features.post.dto.PostDTO
import ru.sobeninalex.domain.features.post.model.Post
import ru.sobeninalex.domain.features.post.model.PostComment
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

fun PostCommentDTO.toPostComment(isOwnComment: Boolean) = PostComment(
    commentId = commentId,
    postId = postId,
    userId = userId,
    content = content,
    userName = userName,
    avatar = avatar?.toCurrentUrl(),
    createdAt = DateFormatter.parseDate(createdAt),
    isOwnComment = isOwnComment
)