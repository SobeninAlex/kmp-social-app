package com.example.kmp_social_app.android.presentation.account.profile

sealed interface ProfileAction {

    data object LoadMorePosts : ProfileAction
}