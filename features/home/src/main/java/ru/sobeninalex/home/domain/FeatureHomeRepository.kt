package ru.sobeninalex.home.domain

import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post

interface FeatureHomeRepository {

    suspend fun createPost(caption: String, imageBytes: ByteArray): Post

    suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean

    suspend fun getFeedPosts(page: Int, pageSize: Int): List<Post>

    suspend fun getFollowingSuggestions(): List<FollowUser>

    suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean
}