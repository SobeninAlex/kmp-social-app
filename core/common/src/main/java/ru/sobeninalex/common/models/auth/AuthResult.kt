package ru.sobeninalex.common.models.auth

data class AuthResult(
    val id: String,
    val name: String,
    val bio: String,
    val avatar: String?,
    val token: String,
    val followersCount: Int,
    val followingCount: Int,
)
