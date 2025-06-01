package ru.sobeninalex.data.remote.services.follows

import ru.sobeninalex.common.models.follow.FollowUser

interface FollowsApiDataSource {

    suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean

    suspend fun getFollowers(userId: String, page: Int, pageSize: Int): List<FollowUser>

    suspend fun getFollowing(userId: String, page: Int, pageSize: Int): List<FollowUser>

    suspend fun getFollowingSuggestions(): List<FollowUser>
}