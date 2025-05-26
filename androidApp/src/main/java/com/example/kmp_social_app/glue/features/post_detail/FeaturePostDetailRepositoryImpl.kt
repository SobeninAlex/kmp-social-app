package com.example.kmp_social_app.glue.features.post_detail

import com.example.kmp_social_app.glue.mappers.toPost
import com.example.kmp_social_app.glue.mappers.toPostComment
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.post.PostComment
import ru.sobeninalex.data.remote.services.post.PostApiDataSource
import ru.sobeninalex.post_detail.domain.FeaturePostDetailRepository
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences

class FeaturePostDetailRepositoryImpl(
    private val postApiDataSource: PostApiDataSource,
    private val userPreferences: UserPreferences,
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
        ).toPost()
    }

    override suspend fun getPostComments(
        postId: String,
        page: Int,
        pageSize: Int
    ): List<PostComment> {
        val userSettings = userPreferences.getUserSettings()
        return postApiDataSource.getPostComments(
            postId = postId,
            page = page,
            pageSize = pageSize
        ).map {
            it.toPostComment(
                isOwnComment = it.userId == userSettings.id
            )
        }
    }

    override suspend fun addComment(postId: String, content: String): PostComment {
        val userSettings = userPreferences.getUserSettings()
        val postCommentDTO = postApiDataSource.addComment(
            postId = postId,
            content = content
        )
        return postCommentDTO.toPostComment(
            isOwnComment = postCommentDTO.userId == userSettings.id
        )
    }

    override suspend fun deleteComment(commentId: String, postId: String) {
        return postApiDataSource.deleteComment(
            commentId = commentId,
            postId = postId
        )
    }
}