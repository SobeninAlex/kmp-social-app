package com.example.kmp_social_app.common.utils

import com.example.kmp_social_app.common.utils.Constants.BASE_URL
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge

fun String.toCurrentUrl(): String {
    return "$BASE_URL${this.substring(21)}"
}

fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
    return merge(this, another)
}