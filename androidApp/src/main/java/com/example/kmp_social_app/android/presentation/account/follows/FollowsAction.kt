package com.example.kmp_social_app.android.presentation.account.follows

sealed interface FollowsAction {

    data object Retry : FollowsAction
}