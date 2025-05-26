package com.example.kmp_social_app.glue.features.home

import com.example.kmp_social_app.glue.mappers.toFollowUser
import com.example.kmp_social_app.glue.mappers.toPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.data.remote.services.follows.FollowsApiService
import ru.sobeninalex.data.remote.services.follows.dto.FollowsRequestDTO
import ru.sobeninalex.data.remote.services.post.PostApiService
import ru.sobeninalex.data.remote.services.post.dto.CreatePostRequestDTO
import ru.sobeninalex.data.remote.services.post.dto.PostLikeRequestDTO
import ru.sobeninalex.data.remote.services.post.dto.PostsResponseDTO
import ru.sobeninalex.home.domain.FeatureHomeRepository
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.SomethingWrongException
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings

class FeatureHomeRepositoryImpl(
    private val userPreferences: UserPreferences,
    private val followsApiService: FollowsApiService,
    private val postApiService: PostApiService,
) : FeatureHomeRepository {

    override suspend fun createPost(caption: String, imageBytes: ByteArray): Post {
        return withContext(Dispatchers.IO) {
            try {
                val userSetting = userPreferences.getUserSettings()

                val postData = Json.encodeToString(
                    serializer = CreatePostRequestDTO.serializer(),
                    value = CreatePostRequestDTO(
                        userId = userSetting.id,
                        caption = caption
                    )
                )

                val response = postApiService.createPost(
                    token = userSetting.token,
                    postData = postData,
                    imageBytes = imageBytes
                )

                if (response.isSuccess) {
                    response.post?.toPost()
                        ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw SomethingWrongException(message = ex.message)
            }
        }
    }

    override suspend fun getFeedPosts(page: Int, pageSize: Int): List<Post> {
        return fetchPosts { userSettings ->
            postApiService.getFeedPosts(
                token = userSettings.token,
                currentUserId = userSettings.id,
                page = page,
                pageSize = pageSize,
            )
        }
    }

    override suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean {
        return withContext(Dispatchers.IO) {
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

    override suspend fun getFollowingSuggestions(): List<FollowUser> {
        return withContext(Dispatchers.IO) {
            try {
                val response = followsApiService.getFollowingSuggestions(
                    token = userPreferences.getUserSettings().token,
                    userId = userPreferences.getUserSettings().id
                )

                if (response.isSuccess) {
                    response.follows.map { it.toFollowUser() }
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }

    override suspend fun followOrUnfollow(
        followedUserId: String,
        shouldFollow: Boolean
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val request = FollowsRequestDTO(
                    follower = userPreferences.getUserSettings().id,
                    following = followedUserId
                )

                val response = if (shouldFollow) {
                    followsApiService.follow(
                        token = userPreferences.getUserSettings().token,
                        request = request
                    )
                } else {
                    followsApiService.unfollow(
                        token = userPreferences.getUserSettings().token,
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

    private suspend fun fetchPosts(
        apiCall: suspend (UserSettings) -> PostsResponseDTO
    ): List<Post> {
        return withContext(Dispatchers.IO) {
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