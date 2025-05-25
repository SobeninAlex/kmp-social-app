package ru.sobeninalex.domain.features.post

import ru.sobeninalex.domain.features.post.model.Post
import ru.sobeninalex.domain.features.post.model.PostComment

interface PostRepository {

    suspend fun getFeedPosts(
        page: Int,
        pageSize: Int
    ): List<Post>

    suspend fun getPost(
        postId: String,
    ): Post

    suspend fun createPost(
        caption: String,
        imageBytes: ByteArray
    ): Post

    suspend fun likeOrUnlikePost(
        postId: String,
        shouldLike: Boolean
    ): Boolean

    suspend fun getPostsByUserId(
        userId: String,
        page: Int,
        pageSize: Int
    ) : List<Post>

    suspend fun getPostComments(
        postId: String,
        page: Int,
        pageSize: Int
    ) : List<PostComment>

    suspend fun addComment(
        postId: String,
        content: String
    ): PostComment

    suspend fun deleteComment(
        commentId: String,
        postId: String,
    )
}