package ru.sobeninalex.home.presentation.create_post

import android.net.Uri

sealed interface CreatePostAction {

    data class OnChangeCaption(val caption: String) : CreatePostAction

    data class OnChangeImageUri(val imageUri: Uri) : CreatePostAction

    data object OnCreatePostClick : CreatePostAction
}