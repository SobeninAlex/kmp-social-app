package ru.sobeninalex.utils.helpers

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.merge
import ru.sobeninalex.utils.helpers.Constants.BASE_URL
import ru.sobeninalex.utils.helpers.Constants.SERVER_URL

fun String.toClientUrl(): String {
    return "$BASE_URL${this.substring(21)}"
}

fun String.toServerUrl(): String {
    return "$SERVER_URL${this.substring(25)}"
}

fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
    return merge(this, another)
}

//content://media/picker/0/com.android.providers.media.photopicker/media/1000000039
val String.isImgUri: Boolean get() = this.contains(".*(photopicker).*".toRegex())

//content://com.example.kmp_social_app.fileprovider/external_cache/movie_17503373802622399534457167365400.mp4
val String.isVideoUri: Boolean get() = this.endsWith(".mp4")