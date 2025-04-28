package com.example.kmp_social_app.common.utils

sealed class NetworkResponse<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Failure<T>(message: String?) : NetworkResponse<T>(message = message)

    class Success<T>(data: T) : NetworkResponse<T>(data = data)
}