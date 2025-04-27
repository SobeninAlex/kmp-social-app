package com.example.kmp_social_app.auth.data

import com.example.kmp_social_app.auth.data.dto.AuthResponseDTO
import com.example.kmp_social_app.auth.data.dto.SingInRequestDTO
import com.example.kmp_social_app.auth.data.dto.SingUpRequestDTO
import com.example.kmp_social_app.common.data.KtorApi
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

internal class AuthService : KtorApi() {

    suspend fun signUp(request: SingUpRequestDTO): AuthResponseDTO = client.post {
        endPoint(path = "/signup")
        setBody(request)
    }.body()

    suspend fun signIn(request: SingInRequestDTO): AuthResponseDTO = client.post {
        endPoint(path = "/signin")
        setBody(request)
    }.body()
}