package com.example.kmp_social_app.auth.domain

import com.example.kmp_social_app.auth.domain.model.AuthResult
import com.example.kmp_social_app.common.utils.NetworkResponse

internal interface AuthRepository {

    suspend fun signUp(
        name: String,
        email: String,
        password: String,
    ): NetworkResponse<AuthResult>

    suspend fun signIn(
        email: String,
        password: String,
    ): NetworkResponse<AuthResult>
}