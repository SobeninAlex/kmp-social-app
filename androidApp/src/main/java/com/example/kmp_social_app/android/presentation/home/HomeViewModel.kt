package com.example.kmp_social_app.android.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.kmp_social_app.android.utils.BaseViewModel
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import com.example.kmp_social_app.feature.post.domain.model.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadContent()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Refresh -> refreshContent()
        }
    }

    private fun loadContent() {
        _uiState.update { it.copy(isLoading = true) }
        updateOnboardingState { it.copy(isLoading = true) }
        loadData()
    }

    private fun refreshContent() {
        _uiState.update { it.copy(isRefreshing = true) }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(1000)

            updateOnboardingState {
                it.copy(
                    isLoading = false,
                    users = FollowUser.FAKE_LIST,
                    shouldShowOnBoarding = true
                )
            }

            delay(1000)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isRefreshing = false,
                    posts = Post.FAKE_LIST
                )
            }
        }
    }

    private fun updateOnboardingState(update: (OnBoardingState) -> OnBoardingState) {
        _uiState.update { oldState ->
            oldState.copy(
                onBoardingState = update(oldState.onBoardingState)
            )
        }
    }

}