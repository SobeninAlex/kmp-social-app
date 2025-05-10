package com.example.kmp_social_app.feature.post.data

import com.example.kmp_social_app.common.data.local.UserPreferences
import com.example.kmp_social_app.common.utils.DispatcherProvider
import com.example.kmp_social_app.common.utils.SomethingWrongException
import com.example.kmp_social_app.feature.post.data.dto.PostLikeRequestDTO
import com.example.kmp_social_app.feature.post.domain.PostRepository
import com.example.kmp_social_app.feature.post.domain.model.Post
import kotlinx.coroutines.withContext

internal class PostRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val userPreferences: UserPreferences,
    private val postApiService: PostApiService,
) : PostRepository {

    override suspend fun getFeedPosts(
        page: Int,
        pageSize: Int,
    ): List<Post> {
        return withContext(dispatcher.io) {
            try {
                val userDate = userPreferences.getUserSettings()

                val response = postApiService.getFeedPosts(
                    token = userDate.token,
                    userId = userDate.id,
                    page = page,
                    pageSize = pageSize,
                )

                if (response.isSuccess) {
                    response.posts.map { it.toPost() }
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    override suspend fun likeOrUnlikePost(
        postId: String,
        shouldLike: Boolean
    ): Boolean {
        return withContext(dispatcher.io) {
            try {
                val userDate = userPreferences.getUserSettings()

                val request = PostLikeRequestDTO(
                    postId = postId,
                    userId = userDate.id
                )

                val response = if (shouldLike) {
                    postApiService.likePost(
                        token = userDate.token,
                        request = request
                    )
                } else {
                    postApiService.unlikePost(
                        token = userDate.token,
                        request = request
                    )
                }

                if (response.isSuccess) {
                    true
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }
}