package ru.sobeninalex.account.domain

import kotlinx.coroutines.flow.StateFlow
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.profile.Profile

interface FeatureAccountRepository {

    suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean

    suspend fun getFollowers(userId: String, page: Int, pageSize: Int): List<FollowUser>

    suspend fun getFollowing(userId: String, page: Int, pageSize: Int): List<FollowUser>

    suspend fun getPostsByUserId(userId: String, page: Int, pageSize: Int) : List<Post>

    fun getProfileById(profileId: String): StateFlow<Profile>

    suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean

    suspend fun updateProfile(profile: Profile, imageBytes: ByteArray?): Profile

    suspend fun deletePost(postId: String): Boolean
}