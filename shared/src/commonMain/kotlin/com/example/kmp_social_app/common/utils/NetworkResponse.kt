package com.example.kmp_social_app.common.utils

sealed interface NetworkResponse<T> {

    class Success<T>(val data: T) : NetworkResponse<T>

    class Failure<T>(val message: String?) : NetworkResponse<T>
}