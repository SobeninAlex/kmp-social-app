package com.example.kmp_social_app.feature.follows.domain

import com.example.kmp_social_app.feature.follows.domain.model.FollowUser

internal interface FollowsRepository {

    suspend fun getFollowingSuggestions(): List<FollowUser>

    suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean
}