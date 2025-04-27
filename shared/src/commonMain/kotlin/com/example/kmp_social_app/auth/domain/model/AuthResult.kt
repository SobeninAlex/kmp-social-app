package com.example.kmp_social_app.auth.domain.model

data class AuthResult(
    val id: String,
    val name: String,
    val bio: String,
    val avatar: String?,
    val token: String,
    val followersCount: Int,
    val followingCount: Int,
)
