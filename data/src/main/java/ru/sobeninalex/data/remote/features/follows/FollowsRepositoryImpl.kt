package ru.sobeninalex.data.remote.features.follows

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import ru.sobeninalex.data.remote.features.follows.dto.FollowsRequestDTO
import ru.sobeninalex.domain.features.follows.FollowsRepository
import ru.sobeninalex.domain.features.follows.model.FollowUser
import ru.sobeninalex.utils.helpers.SomethingWrongException

class FollowsRepositoryImpl(
    private val userPreferences: UserPreferences,
    private val followsApiService: FollowsApiService,
) : FollowsRepository {

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
}