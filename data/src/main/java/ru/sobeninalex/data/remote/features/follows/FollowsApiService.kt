package ru.sobeninalex.data.remote.features.follows

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.sobeninalex.data.remote.KtorApiService
import ru.sobeninalex.data.remote.QueryParams
import ru.sobeninalex.data.remote.SimpleResponseDTO
import ru.sobeninalex.data.remote.features.follows.dto.FollowsRequestDTO
import ru.sobeninalex.data.remote.features.follows.dto.FollowsResponseDTO

class FollowsApiService : KtorApiService() {

    suspend fun follow(token: String, request: FollowsRequestDTO): SimpleResponseDTO {
        return client.post {
            route("/follows/follow")
            setToken(token)
            setBody(request)
        }
            .checkAuth()
            .body<SimpleResponseDTO>()
    }

    suspend fun unfollow(token: String, request: FollowsRequestDTO): SimpleResponseDTO {
        return client.post {
            route("/follows/unfollow")
            setToken(token)
            setBody(request)
        }
            .checkAuth()
            .body<SimpleResponseDTO>()
    }

    suspend fun getFollowingSuggestions(token: String, userId: String): FollowsResponseDTO {
        return client.get {
            route("/follows/suggestions")
            setToken(token)
            parameter(key = QueryParams.USER_ID, value = userId)
        }
            .checkAuth()
            .body<FollowsResponseDTO>()
    }

    suspend fun getFollowers(
        token: String,
        userId: String,
        page: Int,
        pageSize: Int
    ): FollowsResponseDTO {
        return client.get {
            route("/follows/followers")
            setToken(token)
            parameter(key = QueryParams.USER_ID, value = userId)
            parameter(key = QueryParams.PAGE, value = page)
            parameter(key = QueryParams.PAGE_SIZE, value = pageSize)
        }
            .checkAuth()
            .body<FollowsResponseDTO>()
    }

    suspend fun getFollowing(
        token: String,
        userId: String,
        page: Int,
        pageSize: Int
    ): FollowsResponseDTO {
        return client.get {
            route("/follows/following")
            setToken(token)
            parameter(key = QueryParams.USER_ID, value = userId)
            parameter(key = QueryParams.PAGE, value = page)
            parameter(key = QueryParams.PAGE_SIZE, value = pageSize)
        }
            .checkAuth()
            .body<FollowsResponseDTO>()
    }
}