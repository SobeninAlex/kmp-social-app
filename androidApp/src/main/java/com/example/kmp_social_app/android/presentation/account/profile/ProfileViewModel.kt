package com.example.kmp_social_app.android.presentation.account.profile

import androidx.lifecycle.viewModelScope
import com.example.kmp_social_app.android.common.utils.BaseViewModel
import com.example.kmp_social_app.feature.post.domain.model.Post
import com.example.kmp_social_app.feature.profile.domain.model.Profile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userId: String,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadContent()
    }

    fun onAction(action: ProfileAction) {

    }

    private fun loadContent() {
        _uiState.update { it.copy(isLoading = true) }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(2500)

            _uiState.update {
                it.copy(
                    profile = Profile.Preview
                )
            }

            delay(1000)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    posts = Post.PreviewPostList
                )
            }
        }
    }
}