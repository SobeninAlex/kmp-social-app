package ru.sobeninalex.domain.features.follows

import ru.sobeninalex.domain.features.follows.model.FollowUser

interface FollowsRepository {

    suspend fun getFollowingSuggestions(): List<FollowUser>

    suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean

    suspend fun getFollowers(userId: String, page: Int, pageSize: Int): List<FollowUser>

    suspend fun getFollowing(userId: String, page: Int, pageSize: Int): List<FollowUser>
}