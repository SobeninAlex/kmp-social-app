package ru.sobeninalex.post_detail.domain

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.post.PostComment

interface FeaturePostDetailRepository {

    suspend fun addComment(postId: String, content: String): PostComment

    suspend fun deleteComment(commentId: String, postId: String)

    suspend fun getPostComments(postId: String, page: Int, pageSize: Int): List<PostComment>

    suspend fun getPost(postId: String): Post

    suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean
}