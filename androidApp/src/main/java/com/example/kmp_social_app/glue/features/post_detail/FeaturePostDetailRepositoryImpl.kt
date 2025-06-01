package com.example.kmp_social_app.glue.features.post_detail

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.post.PostComment
import ru.sobeninalex.data.remote.services.post.PostApiDataSource
import ru.sobeninalex.post_detail.domain.FeaturePostDetailRepository

class FeaturePostDetailRepositoryImpl(
    private val postApiDataSource: PostApiDataSource,
) : FeaturePostDetailRepository {

    override suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean {
        return postApiDataSource.likeOrUnlikePost(
            postId = postId,
            shouldLike = shouldLike
        )
    }

    override suspend fun getPost(postId: String): Post {
        return postApiDataSource.getPost(
            postId = postId
        )
    }

    override suspend fun getPostComments(
        postId: String,
        page: Int,
        pageSize: Int
    ): List<PostComment> {
        return postApiDataSource.getPostComments(
            postId = postId,
            page = page,
            pageSize = pageSize
        )
    }

    override suspend fun addComment(postId: String, content: String): PostComment {
        return postApiDataSource.addComment(
            postId = postId,
            content = content
        )
    }

    override suspend fun deleteComment(commentId: String, postId: String) {
        return postApiDataSource.deleteComment(
            commentId = commentId,
            postId = postId
        )
    }
}