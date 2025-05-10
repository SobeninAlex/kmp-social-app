package com.example.kmp_social_app.feature.auth.domain

import com.example.kmp_social_app.feature.auth.domain.model.AuthResult

internal interface AuthRepository {

    suspend fun signUp(
        name: String,
        email: String,
        password: String,
    ): AuthResult

    suspend fun signIn(
        email: String,
        password: String,
    ): AuthResult
}