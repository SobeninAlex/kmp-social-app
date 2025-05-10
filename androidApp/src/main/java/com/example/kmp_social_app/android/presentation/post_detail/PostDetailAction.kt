package com.example.kmp_social_app.android.presentation.post_detail

sealed interface PostDetailAction {

    data object Retry : PostDetailAction
}