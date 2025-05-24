package ru.sobeninalex.domain.features.authorization

import ru.sobeninalex.domain.features.authorization.model.AuthResult

interface AuthRepository {

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