package ru.sobeninalex.utils.helpers

import android.content.Context
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageByteReader(
    private val context: Context,
) {

    suspend fun readImageBytes(uri: Uri): ByteArray {
        return withContext(Dispatchers.Default) {
            try {
                val result = context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    inputStream.readBytes()
                }
                result
                    ?: throw SomethingWrongException(message = Constants.UNEXPECTED_ERROR_MESSAGE)
            } catch (ex: Exception) {
                throw ex
            }
        }
    }
}