package ru.sobeninalex.data.remote.services.authorization

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import ru.sobeninalex.data.remote.KtorApiService
import ru.sobeninalex.data.remote.services.authorization.dto.AuthResponseDTO
import ru.sobeninalex.data.remote.services.authorization.dto.SingInRequestDTO
import ru.sobeninalex.data.remote.services.authorization.dto.SingUpRequestDTO

class AuthApiService : KtorApiService() {

    suspend fun signUp(request: SingUpRequestDTO): AuthResponseDTO {
        return client.post {
            route(path = "/signup")
            setBody(request)
        }.body<AuthResponseDTO>()
    }

    suspend fun signIn(request: SingInRequestDTO): AuthResponseDTO = client.post {
        route(path = "/signin")
        setBody(request)
    }.body()
}