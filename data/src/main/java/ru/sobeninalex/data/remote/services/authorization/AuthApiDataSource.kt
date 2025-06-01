package ru.sobeninalex.data.remote.services.authorization

import ru.sobeninalex.common.models.auth.AuthResult

interface AuthApiDataSource {

    suspend fun signUp(name: String, email: String, password: String): AuthResult

    suspend fun signIn(email: String, password: String): AuthResult
}