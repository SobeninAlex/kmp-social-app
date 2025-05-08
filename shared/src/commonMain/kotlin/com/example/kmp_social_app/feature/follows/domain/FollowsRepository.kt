package com.example.kmp_social_app.feature.follows.domain

import com.example.kmp_social_app.common.utils.NetworkResponse
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser

internal interface FollowsRepository {

    suspend fun getFollowingSuggestions(): NetworkResponse<List<FollowUser>>

    suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): NetworkResponse<Boolean>
}