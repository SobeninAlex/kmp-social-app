package com.example.kmp_social_app.android.presentation.post_detail

sealed interface PostDetailAction {

    data object LoadMoreComments : PostDetailAction

    data object CreateComment : PostDetailAction

    data object DeleteComment : PostDetailAction

    data object OnLikeClick : PostDetailAction
}