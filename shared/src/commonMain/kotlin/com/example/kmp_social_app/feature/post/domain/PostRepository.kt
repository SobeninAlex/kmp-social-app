package com.example.kmp_social_app.feature.post.domain

import com.example.kmp_social_app.feature.post.domain.model.Post
import com.example.kmp_social_app.feature.post.domain.model.PostComment

internal interface PostRepository {

    suspend fun getFeedPosts(
        page: Int,
        pageSize: Int
    ): List<Post>

    suspend fun getPost(
        postId: String,
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