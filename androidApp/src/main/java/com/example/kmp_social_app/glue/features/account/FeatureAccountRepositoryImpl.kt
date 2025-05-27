package com.example.kmp_social_app.glue.features.account

import com.example.kmp_social_app.glue.mappers.toFollowUser
import com.example.kmp_social_app.glue.mappers.toPost
import com.example.kmp_social_app.glue.mappers.toProfile
import com.example.kmp_social_app.glue.mappers.toProfileDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import ru.sobeninalex.account.domain.FeatureAccountRepository
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.data.remote.services.account.AccountApiDataSource
import ru.sobeninalex.data.remote.services.follows.FollowsApiDataSource
import ru.sobeninalex.data.remote.services.post.PostApiDataSource

class FeatureAccountRepositoryImpl(
    private val followsApiDataSource: FollowsApiDataSource,
    private val postApiDataSource: PostApiDataSource,
    private val accountApiDataSource: AccountApiDataSource,
) : FeatureAccountRepository {

    override suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean {
        return followsApiDataSource.followOrUnfollow(
            followedUserId = followedUserId,
            shouldFollow = shouldFollow
        )
    }

    override suspend fun getFollowers(userId: String, page: Int, pageSize: Int): List<FollowUser> {
        return followsApiDataSource.getFollowers(
            userId = userId,
            page = page,
            pageSize = pageSize
        ).map { it.toFollowUser() }
    }

    override suspend fun getFollowing(userId: String, page: Int, pageSize: Int): List<FollowUser> {
        return followsApiDataSource.getFollowing(
            userId = userId,
            page = page,
            pageSize = pageSize
        ).map { it.toFollowUser() }
    }

    override suspend fun getPostsByUserId(userId: String, page: Int, pageSize: Int): List<Post> {
        return postApiDataSource.getPostsByUserId(
            userId = userId,
            page = page,
            pageSize = pageSize
        ).map { it.toPost() }
    }

    override suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean {
        return postApiDataSource.likeOrUnlikePost(
            postId = postId,
            shouldLike = shouldLike
        )
    }

    override fun getProfileById(profileId: String): StateFlow<Profile> {
        return accountApiDataSource.getProfileById(
            profileId = profileId
        )
    }

    override suspend fun updateProfile(profile: Profile, imageBytes: ByteArray?): Profile {
        return accountApiDataSource.updateProfile(
            profile = profile.toProfileDTO(),
            imageBytes = imageBytes
        ).toProfile()
    }
}