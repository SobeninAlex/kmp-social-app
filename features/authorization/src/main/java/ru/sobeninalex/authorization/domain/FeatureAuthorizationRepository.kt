package ru.sobeninalex.authorization.domain

import ru.sobeninalex.common.models.auth.AuthResult

interface FeatureAuthorizationRepository {

    suspend fun signUp(name: String, email: String, password: String): AuthResult

    suspend fun signIn(email: String, password: String): AuthResult
}