package ru.sobeninalex.utils.helpers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import ru.sobeninalex.utils.helpers.Constants.BASE_URL
import ru.sobeninalex.utils.helpers.Constants.SERVER_URL

fun String.toCurrentUrl(): String {
    return "$BASE_URL${this.substring(21)}"
}

fun String.toServerUrl(): String {
    return "$SERVER_URL${this.substring(25)}"
}

fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
    return merge(this, another)
}