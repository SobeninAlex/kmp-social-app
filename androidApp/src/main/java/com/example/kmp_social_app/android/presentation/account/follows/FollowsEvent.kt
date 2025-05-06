package com.example.kmp_social_app.android.presentation.account.follows

sealed interface FollowsEvent {

    data object Retry : FollowsEvent
}