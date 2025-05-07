package com.example.kmp_social_app.android.presentation.main

import com.example.kmp_social_app.common.data.local.UserSettings

sealed interface MainUiState {

    data object Loading : MainUiState

    data class Success(val currentUser: UserSettings) : MainUiState
}