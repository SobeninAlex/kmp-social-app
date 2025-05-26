package ru.sobeninalex.data.remote.services.post

import ru.sobeninalex.data.remote.services.post.dto.PostCommentDTO
import ru.sobeninalex.data.remote.services.post.dto.PostDTO

interface PostApiDataSource {

    suspend fun getPostsByUserId(userId: String, page: Int, pageSize: Int): List<PostDTO>

    suspend fun getPost(postId: String): PostDTO

    suspend fun getPostComments(postId: String, page: Int, pageSize: Int): List<PostCommentDTO>

    suspend fun likeOrUnlikePost(postId: String, shouldLike: Boolean): Boolean

    suspend fun addComment(postId: String, content: String): PostCommentDTO

    suspend fun deleteComment(commentId: String, postId: String)

    suspend fun createPost(caption: String, imageBytes: ByteArray): PostDTO

    suspend fun getFeedPosts(page: Int, pageSize: Int): List<PostDTO>
}