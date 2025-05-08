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
            is HomeEvent.OnBoardingFinishClick -> hideOnboarding()
        }
    }

    private fun hideOnboarding() {
        _uiState.update {
            it.copy(
                showUsersRecommendation = false
            )
        }
    }

    private fun loadContent() {
        _uiState.update { it.copy(isLoading = true) }
        loadData()
    }

    private fun refreshContent() {
        _uiState.update { it.copy(isRefreshing = true) }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(2500)

            _uiState.update {
                it.copy(
                    users = FollowUser.PreviewFollowUserList,
                )
            }

            delay(1000)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    isRefreshing = false,
                    posts = Post.PreviewPostList
                )
            }
        }
    }
}