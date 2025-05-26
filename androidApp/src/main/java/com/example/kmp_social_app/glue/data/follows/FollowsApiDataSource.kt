package com.example.kmp_social_app.glue.data.follows

import com.example.kmp_social_app.glue.mappers.toFollowUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.data.remote.services.follows.FollowsApiService
import ru.sobeninalex.data.remote.services.follows.dto.FollowsRequestDTO
import ru.sobeninalex.utils.helpers.SomethingWrongException
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences

class FollowsApiDataSource(
    private val userPreferences: UserPreferences,
    private val followsApiService: FollowsApiService,
) {

    suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean {
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

    suspend fun getFollowers(userId: String, page: Int, pageSize: Int): List<FollowUser> {
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

    suspend fun getFollowing(userId: String, page: Int, pageSize: Int): List<FollowUser> {
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

    suspend fun getFollowingSuggestions(): List<FollowUser> {
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
}