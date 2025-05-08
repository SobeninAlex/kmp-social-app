package com.example.kmp_social_app.android.presentation.home

sealed interface HomeEvent {

    data object Refresh : HomeEvent

    data object OnBoardingFinishClick : HomeEvent
}