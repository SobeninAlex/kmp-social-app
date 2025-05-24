package ru.sobeninalex.data.remote.features.post

import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.sobeninalex.data.remote.KtorApiService
import ru.sobeninalex.data.remote.QueryParams
import ru.sobeninalex.data.remote.SimpleResponseDTO
import ru.sobeninalex.data.remote.features.post.dto.CommentResponseDTO
import ru.sobeninalex.data.remote.features.post.dto.ListCommentResponseDTO
import ru.sobeninalex.data.remote.features.post.dto.NewCommentRequestDTO
import ru.sobeninalex.data.remote.features.post.dto.PostLikeRequestDTO
import ru.sobeninalex.data.remote.features.post.dto.PostResponseDTO
import ru.sobeninalex.data.remote.features.post.dto.PostsResponseDTO

internal class PostApiService : KtorApiService() {

    suspend fun likePost(token: String, request: PostLikeRequestDTO): SimpleResponseDTO {
        return client.post {
            route("/post/likes/add")
            setToken(token)
            setBody(request)
        }
            .checkAuth()
            .body<SimpleResponseDTO>()
    }

    suspend fun unlikePost(token: String, request: PostLikeRequestDTO): SimpleResponseDTO {
        return client.post {
            route("/post/likes/remove")
            setToken(token)
            setBody(request)
        }
            .checkAuth()
            .body<SimpleResponseDTO>()
    }

    suspend fun getPostsByUserId(
        token: String,
        userId: String,
        currentUserId: String,
        page: Int,
        pageSize: Int
    ) : PostsResponseDTO {
        return client.get {
            route("/posts/$userId")
            setToken(token)
            parameter(key = QueryParams.CURRENT_USER_ID, value = currentUserId)
            parameter(key = QueryParams.PAGE, value = page)
            parameter(key = QueryParams.PAGE_SIZE, value = pageSize)
        }
            .checkAuth()
            .body()
    }

    suspend fun getFeedPosts(
        token: String,
        currentUserId: String,
        page: Int,
        pageSize: Int
    ): PostsResponseDTO {
        return client.get {
            route("/posts/feed")
            setToken(token)
            parameter(key = QueryParams.USER_ID, value = currentUserId)
            parameter(key = QueryParams.PAGE, value = page)
            parameter(key = QueryParams.PAGE_SIZE, value = pageSize)
        }
            .checkAuth()
            .body<PostsResponseDTO>()
    }

    suspend fun getPost(
        token: String,
        postId: String,
        currentUserId: String,
    ): PostResponseDTO {
        return client.get {
            route("/post/$postId")
            setToken(token)
            parameter(key = QueryParams.CURRENT_USER_ID, value = currentUserId)
        }
            .checkAuth()
            .body()
    }

    suspend fun getPostComments(
        token: String,
        postId: String,
        page: Int,
        pageSize: Int
    ) : ListCommentResponseDTO {
        return client.get {
            route("/post/comments/$postId")
            setToken(token)
            parameter(key = QueryParams.PAGE, value = page)
            parameter(key = QueryParams.PAGE_SIZE, value = pageSize)
        }
            .checkAuth()
            .body()
    }

    suspend fun addComment(
        token: String,
        request: NewCommentRequestDTO,
    ): CommentResponseDTO {
        return client.post {
            route("/post/comments/create")
            setToken(token)
            setBody(request)
        }
            .checkAuth()
            .body()
    }

    suspend fun deleteComment(
        token: String,
        commentId: String,
        postId: String,
        userId: String,
    ): SimpleResponseDTO {
        return client.delete {
            route("/post/comments/delete/$commentId")
            setToken(token)
            parameter(key = QueryParams.USER_ID, value = userId)
            parameter(key = QueryParams.POST_ID, value = postId)
        }
            .checkAuth()
            .body()
    }
}