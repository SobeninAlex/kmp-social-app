package com.example.kmp_social_app.common.data.remote

import com.example.kmp_social_app.common.utils.Constants
import com.example.kmp_social_app.common.utils.HttpLog
import com.example.kmp_social_app.common.utils.UnauthorizedException
import io.ktor.client.HttpClient
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal abstract class KtorApiService: KoinComponent {

    private val httpLog by inject<HttpLog>()

    val client = HttpClient {
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
                    httpLog.log("KtorClient", message)
                }
            }
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 60_000
            connectTimeoutMillis = 60_000
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

    fun HttpResponse.checkAuth(): HttpResponse {
        return if (this.status == HttpStatusCode.Unauthorized) {
            throw UnauthorizedException()
        } else {
            this
        }
    }
}