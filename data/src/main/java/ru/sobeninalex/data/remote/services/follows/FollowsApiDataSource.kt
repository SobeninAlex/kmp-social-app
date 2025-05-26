package ru.sobeninalex.data.remote.services.follows

import ru.sobeninalex.data.remote.services.follows.dto.FollowUserDTO

interface FollowsApiDataSource {

    suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean

    suspend fun getFollowers(userId: String, page: Int, pageSize: Int): List<FollowUserDTO>

    suspend fun getFollowing(userId: String, page: Int, pageSize: Int): List<FollowUserDTO>

    suspend fun getFollowingSuggestions(): List<FollowUserDTO>
}