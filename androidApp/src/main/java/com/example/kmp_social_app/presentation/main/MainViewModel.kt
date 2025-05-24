package com.example.kmp_social_app.presentation.main

import androidx.datastore.core.DataStore
import ru.sobeninalex.common.presentation.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.sobeninalex.utils.preferences.user_prefs.UserSettings

class MainViewModel(
    dataStoreUserSettings: DataStore<UserSettings>
) : BaseViewModel() {

    val uiState = dataStoreUserSettings.data.map {
        MainUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}