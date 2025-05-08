package com.example.kmp_social_app.common.data.remote

import com.example.kmp_social_app.common.utils.HttpLog
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/** Если запущен сервак локально то тут надо указвать не адрес локал хочта,
 * а IP-адрес компьютера в локальной сети.
 * В консоли ipconfig
 * нужен IPv4-адрес */
private const val BASE_URL = "http://192.168.1.153:8080"

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
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
        }
    }

    fun HttpRequestBuilder.route(path: String) {
        url {
            takeFrom(BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }

    fun HttpRequestBuilder.setToken(token: String) {
        headers {
            append(name = "Authorization", value = "Bearer $token")
        }
    }
}