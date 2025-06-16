package ru.sobeninalex.home.presentation.create_post

import android.net.Uri

internal sealed interface CreatePostAction {

    data class OnChangeCaption(val caption: String) : CreatePostAction

    data object OnCreatePostClick : CreatePostAction

    data class OnPickAttachments(val uris: List<Uri>) : CreatePostAction

    data class ShowMediaPager(val uri: Uri) : CreatePostAction

    data object HideMediaPager : CreatePostAction
}