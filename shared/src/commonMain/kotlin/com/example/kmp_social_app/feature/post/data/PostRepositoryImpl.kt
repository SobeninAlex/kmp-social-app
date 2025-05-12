package com.example.kmp_social_app.feature.post.data

import com.example.kmp_social_app.common.data.local.UserPreferences
import com.example.kmp_social_app.common.data.local.UserSettings
import com.example.kmp_social_app.common.utils.DispatcherProvider
import com.example.kmp_social_app.common.utils.SomethingWrongException
import com.example.kmp_social_app.feature.post.data.dto.PostLikeRequestDTO
import com.example.kmp_social_app.feature.post.data.dto.PostsResponseDTO
import com.example.kmp_social_app.feature.post.domain.PostRepository
import com.example.kmp_social_app.feature.post.domain.model.Post
import kotlinx.coroutines.withContext

internal class PostRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val userPreferences: UserPreferences,
    private val postApiService: PostApiService,
) : PostRepository {

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

    override suspend fun getFeedPosts(
        page: Int,
        pageSize: Int,
    ): List<Post> {
        return fetchPosts { userSettings ->
            postApiService.getFeedPosts(
                token = userSettings.token,
                currentUserId = userSettings.id,
                page = page,
                pageSize = pageSize,
            )
        }
    }

    override suspend fun getPostsByUserId(
        userId: String,
        page: Int,
        pageSize: Int
    ): List<Post> {
        return fetchPosts { userSettings ->
            postApiService.getPostsByUserId(
                token = userSettings.token,
                userId = userId,
                currentUserId = userSettings.id,
                page = page,
                pageSize = pageSize
            )
        }
    }

    private suspend fun fetchPosts(
        apiCall: suspend (UserSettings) -> PostsResponseDTO
    ): List<Post> {
        return withContext(dispatcher.io) {
            try {
                val userDate = userPreferences.getUserSettings()

                val response = apiCall(userDate)

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
}