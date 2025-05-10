package com.example.kmp_social_app.feature.post.domain

import com.example.kmp_social_app.feature.post.domain.model.Post

internal interface PostRepository {

    suspend fun getFeedPosts(
        page: Int,
        pageSize: Int
    ): List<Post>

    suspend fun likeOrUnlikePost(
        postId: String,
        shouldLike: Boolean
    ): Boolean
}