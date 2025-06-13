package ru.sobeninalex.utils.helpers

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageByteReader(
    private val context: Context,
) {

    suspend fun readImageBytes(uris: List<Uri>): List<ByteArray> {
        return withContext(Dispatchers.Default) {
            try {
                uris.map {
                    context.contentResolver.openInputStream(it)?.use { inputStream ->
                        inputStream.readBytes()
                    } ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
                }
            } catch (ex: Exception) {
                throw ex
            }
        }
    }
}