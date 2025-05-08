package com.example.kmp_social_app.feature.post.data

import com.example.kmp_social_app.common.data.remote.KtorApiService
import com.example.kmp_social_app.common.utils.QueryParams
import com.example.kmp_social_app.common.utils.SimpleResponseDTO
import com.example.kmp_social_app.feature.post.data.dto.PostLikeRequestDTO
import com.example.kmp_social_app.feature.post.data.dto.PostsResponseDTO
import io.ktor.client.call.body
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
        }.body<SimpleResponseDTO>()
    }

    suspend fun unlikePost(token: String, request: PostLikeRequestDTO): SimpleResponseDTO {
        return client.post {
            route("/post/likes/remove")
            setToken(token)
            setBody(request)
        }.body<SimpleResponseDTO>()
    }

    suspend fun getFeedPosts(
        token: String,
        userId: String,
        page: Int,
        pageSize: Int
    ) : PostsResponseDTO {
        return client.get {
            route("/posts/feed")
            setToken(token)
            parameter(key = QueryParams.USER_ID, value = userId)
            parameter(key = QueryParams.PAGE, value = page)
            parameter(key = QueryParams.PAGE_SIZE, value = pageSize)
        }.body<PostsResponseDTO>()
    }
}