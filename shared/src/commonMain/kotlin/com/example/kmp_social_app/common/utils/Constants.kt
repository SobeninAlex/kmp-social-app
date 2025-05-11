package com.example.kmp_social_app.common.utils

object Constants {
    /** Если запущен сервак локально то тут надо указвать не адрес локал хоста,
     * а IP-адрес компьютера в локальной сети.
     * В консоли ipconfig
     * нужен IPv4-адрес */
    internal const val BASE_URL = "http://192.168.1.153:8080"

    const val INITIAL_PAGE = 0
    const val UNEXPECTED_ERROR_MESSAGE = "Oops! Something went wrong ;("
    const val DEFAULT_PAGE_SIZE = 3
}