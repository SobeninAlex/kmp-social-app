package com.example.kmp_social_app.feature.account.data

import com.example.kmp_social_app.common.data.remote.KtorApiService
import com.example.kmp_social_app.common.utils.QueryParams
import com.example.kmp_social_app.feature.account.data.dto.ProfileInfoResponseDTO
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class AccountApiService : KtorApiService() {

    suspend fun getProfileById(
        token: String,
        profileId: String,
        currentUserId: String
    ): ProfileInfoResponseDTO {
        return client.get {
            route("/user/$profileId")
            setToken(token)
            parameter(key = QueryParams.CURRENT_USER_ID, value = currentUserId)
        }
            .checkAuth()
            .body()
    }
}