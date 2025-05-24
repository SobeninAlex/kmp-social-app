package com.example.kmp_social_app.presentation.main

import androidx.datastore.core.DataStore
import com.example.kmp_social_app.common.data.local.UserSettings
import ru.sobeninalex.utils.presentation.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    dataStore: DataStore<UserSettings>
) : ru.sobeninalex.utils.presentation.BaseViewModel() {

    val uiState = dataStore.data.map {
        MainUiState.Success(it)
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainUiState.Loading,
        started = SharingStarted.WhileSubscribed(5_000)
    )
}