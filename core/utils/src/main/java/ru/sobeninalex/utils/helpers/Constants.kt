package ru.sobeninalex.utils.helpers

object Constants {
    /** Если запущен сервак локально то тут надо указвать не адрес локал хоста,
     * а IP-адрес компьютера в локальной сети.
     * В консоли ipconfig
     * нужен IPv4-адрес */
    const val BASE_URL = "http://192.168.1.153:8080"
    const val REQUEST_TIMEOUT = 10_000L

    const val INITIAL_PAGE = 0
    const val UNEXPECTED_ERROR_MESSAGE = "Oops! Something went wrong ;("
    const val NO_INTERNET_ERROR = "No Internet"
    const val DEFAULT_PAGE_SIZE = 3
}