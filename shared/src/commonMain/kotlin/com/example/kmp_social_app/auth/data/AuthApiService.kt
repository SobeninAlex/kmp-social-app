package com.example.kmp_social_app.auth.data

import com.example.kmp_social_app.auth.data.dto.AuthResponseDTO
import com.example.kmp_social_app.auth.data.dto.SingInRequestDTO
import com.example.kmp_social_app.auth.data.dto.SingUpRequestDTO
import com.example.kmp_social_app.common.data.KtorApiService
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class AuthApiService : KtorApiService() {

    suspend fun signUp(request: SingUpRequestDTO): AuthResponseDTO = client.post {
        endPoint(path = "/signup")
        setBody(request)
    }.body()

    suspend fun signIn(request: SingInRequestDTO): AuthResponseDTO = client.post {
        endPoint(path = "/signin")
        setBody(request)
    }.body()
}