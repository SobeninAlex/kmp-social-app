package ru.sobeninalex.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.sobeninalex.data.remote.services.authorization.dto.AuthResponseDTO
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.UnauthorizedException

abstract class KtorApiService {

    val client = HttpClient(/*CIO*/) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
                prettyPrint = true
            })
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("KtorClient", message)
                }
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = Constants.REQUEST_TIMEOUT
            connectTimeoutMillis = Constants.REQUEST_TIMEOUT
        }
    }

    fun HttpRequestBuilder.route(path: String) {
        url {
            takeFrom(Constants.BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }

    fun HttpRequestBuilder.setToken(token: String) {
        headers {
            append(name = "Authorization", value = "Bearer $token")
        }
    }

    suspend fun HttpResponse.checkAuth(): HttpResponse {
        return if (this.status == HttpStatusCode.Unauthorized) {
            throw UnauthorizedException(message = this.body<AuthResponseDTO>().errorMessage)
        } else {
            this
        }
    }
}