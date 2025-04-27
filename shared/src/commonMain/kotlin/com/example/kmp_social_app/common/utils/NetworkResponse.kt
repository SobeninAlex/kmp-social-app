package com.example.kmp_social_app.common.utils

sealed class NetworkResponse<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Failure<T>(data: T? = null, message: String?) : NetworkResponse<T>(data = data, message = message)

    class Success<T>(data: T) : NetworkResponse<T>(data = data)
}