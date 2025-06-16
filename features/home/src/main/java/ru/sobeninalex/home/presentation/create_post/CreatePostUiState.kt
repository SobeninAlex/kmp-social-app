package ru.sobeninalex.home.presentation.create_post

import android.net.Uri
import androidx.compose.runtime.Immutable

@Immutable
internal data class CreatePostUiState(
    val isLoading: Boolean = false,
    val caption: String = "",
    val attachmentsUri: List<Uri> = emptyList(),
    val uploadPostSuccess: Boolean = false,
    val mediaPagerState: MediaPagerState = MediaPagerState()
) {

    val createPostButtonEnabled: Boolean get() = caption.isNotBlank() && attachmentsUri.isNotEmpty()
}

@Immutable
data class MediaPagerState(
    val show: Boolean = false,
    val uri: Uri = Uri.EMPTY
)