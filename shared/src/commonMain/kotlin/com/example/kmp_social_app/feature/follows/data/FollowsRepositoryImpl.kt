package com.example.kmp_social_app.feature.follows.data

import com.example.kmp_social_app.common.data.local.UserPreferences
import com.example.kmp_social_app.common.utils.DispatcherProvider
import com.example.kmp_social_app.common.utils.NetworkResponse
import com.example.kmp_social_app.feature.follows.data.dto.FollowsRequestDTO
import com.example.kmp_social_app.feature.follows.domain.FollowsRepository
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import kotlinx.coroutines.withContext

internal class FollowsRepositoryImpl(
    private val dispatcher: DispatcherProvider,
    private val userPreferences: UserPreferences,
    private val followsApiService: FollowsApiService,
) : FollowsRepository {

    override suspend fun getFollowingSuggestions(): NetworkResponse<List<FollowUser>> {
        return withContext(dispatcher.io) {
            try {
                val response = followsApiService.getFollowingSuggestions(
                    token = userPreferences.getUserSettings().token,
                    userId = userPreferences.getUserSettings().id
                )

                if (response.isSuccess) {
                    NetworkResponse.Success(data = response.follows.map {
                        it.toFollowUser() })
                } else {
                    NetworkResponse.Failure(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                NetworkResponse.Failure(message = ex.message)
            }
        }
    }

    override suspend fun followOrUnfollow(
        followedUserId: String,
        shouldFollow: Boolean
    ): NetworkResponse<Boolean> {
        return withContext(dispatcher.io) {
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
                    NetworkResponse.Success(data = response.isSuccess)
                } else {
                    NetworkResponse.Failure(message = response.errorMessage)
                }
            } catch (ex: Exception) {
                NetworkResponse.Failure(message = ex.message)
            }
        }
    }
}