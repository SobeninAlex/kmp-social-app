package com.example.kmp_social_app.glue.features.account

import com.example.kmp_social_app.glue.mappers.toFollowUser
import com.example.kmp_social_app.glue.mappers.toPost
import com.example.kmp_social_app.glue.mappers.toProfile
import com.example.kmp_social_app.glue.mappers.toUserSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import ru.sobeninalex.account.domain.FeatureAccountRepository
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.data.remote.services.account.AccountApiService
import ru.sobeninalex.data.remote.services.account.dto.UpdateProfileRequestDTO
import ru.sobeninalex.data.remote.services.follows.FollowsApiService
import ru.sobeninalex.data.remote.services.follows.dto.FollowsRequestDTO
import ru.sobeninalex.data.remote.services.post.PostApiService
import ru.sobeninalex.data.remote.services.post.dto.PostLikeRequestDTO
import ru.sobeninalex.data.remote.services.post.dto.PostsResponseDTO
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.SomethingWrongException
import ru.sobeninalex.utils.helpers.toServerUrl
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings

class FeatureAccountRepositoryImpl(
    private val userPreferences: UserPreferences,
    private val accountApiService: AccountApiService,
    private val followsApiService: FollowsApiService,
    private val postApiService: PostApiService,
) : FeatureAccountRepository {

    override suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean {
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

    override suspend fun getFollowers(userId: String, page: Int, pageSize: Int): List<FollowUser> {
        return withContext(Dispatchers.IO) {
            try {
                val userSettings = userPreferences.getUserSettings()

                val response = followsApiService.getFollowers(
                    token = userSettings.token,
                    userId = userId,
                    page = page,
                    pageSize = pageSize
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

    override suspend fun getFollowing(userId: String, page: Int, pageSize: Int): List<FollowUser> {
        return withContext(Dispatchers.IO) {
            try {
                val userSettings = userPreferences.getUserSettings()

                val response = followsApiService.getFollowing(
                    token = userSettings.token,
                    userId = userId,
                    page = page,
                    pageSize = pageSize
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

    override suspend fun getPostsByUserId(userId: String, page: Int, pageSize: Int): List<Post> {
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

    override fun getProfileById(profileId: String): Flow<Profile> {
        return flow {
            val userSettings = userPreferences.getUserSettings()

            if (profileId == userSettings.id) {
                emit(userSettings.toProfile())
            }

            val response = accountApiService.getProfileById(
                token = userSettings.token,
                profileId = profileId,
                currentUserId = userSettings.id
            )

            if (response.isSuccess) {
                response.user?.let {
                    emit(it.toProfile())
                } ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
            } else {
                throw SomethingWrongException(message = response.errorMessage)
            }
        }.catch { exception ->
            throw exception
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateProfile(profile: Profile, imageBytes: ByteArray?): Profile {
        return withContext(Dispatchers.IO) {
            try {
                val userSetting = userPreferences.getUserSettings()

                val userData = Json.encodeToString(
                    serializer = UpdateProfileRequestDTO.serializer(),
                    value = UpdateProfileRequestDTO(
                        userId = profile.id,
                        name = profile.name,
                        bio = profile.bio,
                        avatar = profile.avatar?.toServerUrl()
                    )
                )

                val response = accountApiService.updateProfile(
                    token = userSetting.token,
                    userData = userData,
                    imageBytes = imageBytes
                )

                if (response.isSuccess) {
                    var avatar = profile.avatar
                    if (imageBytes != null) {
                        val profileResponse = accountApiService.getProfileById(
                            token = userSetting.token,
                            profileId = profile.id,
                            currentUserId = profile.id
                        )

                        profileResponse.user?.let {
                            avatar = it.avatar
                        }
                    }
                    val updateProfile = profile.copy(avatar = avatar)
                    userPreferences.setUserSettings(
                        updateProfile.toUserSettings(userSetting.token)
                    )
                    updateProfile
                } else {
                    throw SomethingWrongException(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                throw SomethingWrongException(message = ex.message)
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