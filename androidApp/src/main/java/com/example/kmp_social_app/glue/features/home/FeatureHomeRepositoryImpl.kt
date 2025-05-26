package com.example.kmp_social_app.glue.features.home

import com.example.kmp_social_app.glue.mappers.toFollowUser
import com.example.kmp_social_app.glue.mappers.toPost
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.data.remote.services.follows.FollowsApiDataSource
import ru.sobeninalex.data.remote.services.post.PostApiDataSource
import ru.sobeninalex.home.domain.FeatureHomeRepository

class FeatureHomeRepositoryImpl(
    private val postApiDataSource: PostApiDataSource,
    private val followsApiDataSource: FollowsApiDataSource,
) : FeatureHomeRepository {

    override suspend fun createPost(caption: String, imageBytes: ByteArray): Post {
        return postApiDataSource.createPost(
            caption = caption,
            imageBytes = imageBytes
        ).toPost()
    }

    override suspend fun getFeedPosts(page: Int, pageSize: Int): List<Post> {
        return postApiDataSource.getFeedPosts(
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

    override suspend fun getFollowingSuggestions(): List<FollowUser> {
        return followsApiDataSource
            .getFollowingSuggestions()
            .map { it.toFollowUser() }
    }

    override suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean {
        return followsApiDataSource.followOrUnfollow(
            followedUserId = followedUserId,
            shouldFollow = shouldFollow
        )
    }
}