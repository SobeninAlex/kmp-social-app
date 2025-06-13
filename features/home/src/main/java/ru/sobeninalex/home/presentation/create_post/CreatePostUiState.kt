package ru.sobeninalex.home.presentation.create_post

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
internal data class CreatePostUiState(
    val isLoading: Boolean = false,
    val caption: String = "",
    val attachmentsUri: List<Uri> = emptyList(),
    val uploadPostSuccess: Boolean = false
) {

    val createPostButtonEnabled: Boolean get() = caption.isNotBlank() && attachmentsUri.isNotEmpty()
}