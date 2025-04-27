package com.example.kmp_social_app.android.presentation.home

import androidx.lifecycle.ViewModel
import com.example.kmp_social_app.android.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: HomeEvent) {

    }

}