package com.example.kmp_social_app.glue.features.home

import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.data.remote.services.follows.FollowsApiDataSource
import ru.sobeninalex.data.remote.services.post.PostApiDataSource
import ru.sobeninalex.home.domain.FeatureHomeRepository

class FeatureHomeRepositoryImpl(
    private val postApiDataSource: PostApiDataSource,
    private val followsApiDataSource: FollowsApiDataSource,
) : FeatureHomeRepository {

    override suspend fun createPost(caption: String, imageBytes: List<ByteArray>): Post {
        return postApiDataSource.createPost(
            caption = caption,
            imageBytes = imageBytes
        )
    }

    override suspend fun getFeedPosts(page: Int, pageSize: Int): List<Post> {
        return postApiDataSource.getFeedPosts(
            page = page,
            pageSize = pageSize
        )
    }

    override suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean {
        return postApiDataSource.likeOrUnlikePost(
            postId = postId,
            shouldLike = shouldLike
        )
    }

    override suspend fun getFollowingSuggestions(): List<FollowUser> {
        return followsApiDataSource.getFollowingSuggestions()
    }

    override suspend fun followOrUnfollow(followedUserId: String, shouldFollow: Boolean): Boolean {
        return followsApiDataSource.followOrUnfollow(
            followedUserId = followedUserId,
            shouldFollow = shouldFollow
        )
    }

    override suspend fun deletePost(postId: String): Boolean {
        return postApiDataSource.deletePost(postId = postId)
    }
}