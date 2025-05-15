package com.example.kmp_social_app.feature.post.data

import com.example.kmp_social_app.common.data.remote.KtorApiService
import com.example.kmp_social_app.common.utils.QueryParams
import com.example.kmp_social_app.common.utils.SimpleResponseDTO
import com.example.kmp_social_app.feature.post.data.dto.CommentResponseDTO
import com.example.kmp_social_app.feature.post.data.dto.ListCommentResponseDTO
import com.example.kmp_social_app.feature.post.data.dto.NewCommentRequestDTO
import com.example.kmp_social_app.feature.post.data.dto.PostLikeRequestDTO
import com.example.kmp_social_app.feature.post.data.dto.PostResponseDTO
import com.example.kmp_social_app.feature.post.data.dto.PostsResponseDTO
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

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

    suspend fun getPost(
        token: String,
        postId: String,
        userId: String,
    ): PostResponseDTO {
        return client.get {
            route("/post/$postId")
            setToken(token)
            parameter(key = QueryParams.USER_ID, value = userId)
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