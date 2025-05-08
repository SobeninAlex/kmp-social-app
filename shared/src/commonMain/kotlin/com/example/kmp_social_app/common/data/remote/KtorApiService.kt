package com.example.kmp_social_app.common.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/** Если запущен сервак локально то тут надо указвать не адрес локал хочта,
 * а IP-адрес компьютера в локальной сети.
 * В консоли ipconfig
 * нужен IPv4-адрес */
private const val BASE_URL = "http://192.168.1.153:8080"

internal abstract class KtorApiService {

    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }

        install(Logging) {
            level = LogLevel.ALL
            logger = AndroidLogger
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
        }
    }

    fun HttpRequestBuilder.endPoint(path: String) {
        url {
            takeFrom(BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }
}

private object AndroidLogger : Logger {
    private const val TAG = "KtorClient"
    override fun log(message: String) {
        val maxLogSize = 4000
        for (i in 0..message.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > message.length) message.length else end
            println("$TAG -> ${message.substring(start, end)}")
        }
    }
}