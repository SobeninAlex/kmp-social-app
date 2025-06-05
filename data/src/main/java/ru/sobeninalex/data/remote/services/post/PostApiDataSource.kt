package ru.sobeninalex.data.remote.services.post

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.post.PostComment

interface PostApiDataSource {

    suspend fun getPostsByUserId(userId: String, page: Int, pageSize: Int): List<Post>

    suspend fun getPost(postId: String): Post

    suspend fun getPostComments(postId: String, page: Int, pageSize: Int): List<PostComment>

    suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean

    suspend fun addComment(postId: String, content: String): PostComment

    suspend fun deleteComment(commentId: String, postId: String)

    suspend fun createPost(caption: String, imageBytes: ByteArray): Post

    suspend fun getFeedPosts(page: Int, pageSize: Int): List<Post>

    suspend fun deletePost(postId: String): Boolean
}