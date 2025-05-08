package com.example.kmp_social_app.feature.follows.data

import com.example.kmp_social_app.common.data.remote.KtorApiService
import com.example.kmp_social_app.common.data.BaseResponseDTO
import com.example.kmp_social_app.common.utils.QueryParams
import com.example.kmp_social_app.feature.follows.data.dto.FollowsRequestDTO
import com.example.kmp_social_app.feature.follows.data.dto.FollowsResponseDTO
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class FollowsApiService : KtorApiService() {

    suspend fun follow(token: String, request: FollowsRequestDTO): BaseResponseDTO {
        return client.post {
            endPoint("/follows/follow")
            setToken(token)
            setBody(request)
        }.body<BaseResponseDTO>()
    }

    suspend fun unfollow(token: String, request: FollowsRequestDTO): BaseResponseDTO {
        return client.post {
            endPoint("/follows/unfollow")
            setToken(token)
            setBody(request)
        }.body<BaseResponseDTO>()
    }

    suspend fun getFollowingSuggestions(token: String, userId: String): FollowsResponseDTO {
        return client.get {
            endPoint("/follows/suggestions")
            setToken(token)
            parameter(key = QueryParams.USER_ID, value = userId)
        }.body<FollowsResponseDTO>()
    }
}