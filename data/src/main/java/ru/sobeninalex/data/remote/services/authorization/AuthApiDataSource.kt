package ru.sobeninalex.data.remote.services.authorization

import ru.sobeninalex.data.remote.services.authorization.dto.AuthResultDTO

interface AuthApiDataSource {

    suspend fun signUp(name: String, email: String, password: String): AuthResultDTO

    suspend fun signIn(email: String, password: String): AuthResultDTO
}