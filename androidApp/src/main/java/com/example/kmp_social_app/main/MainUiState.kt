package com.example.kmp_social_app.main

import ru.sobeninalex.utils.preferences.user_prefs.UserSettings

sealed interface MainUiState {

    data object Loading : MainUiState

    data class Success(val currentUser: UserSettings) : MainUiState
}