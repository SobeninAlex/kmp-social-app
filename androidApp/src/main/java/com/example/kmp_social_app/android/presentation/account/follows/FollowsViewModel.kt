package com.example.kmp_social_app.android.presentation.account.follows

import androidx.lifecycle.viewModelScope
import com.example.kmp_social_app.android.common.utils.BaseViewModel
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FollowsViewModel(
    private val args: FollowsArgs
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(FollowsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(followsType = args.followsType) }
        loadData()
    }

    fun onAction(action: FollowsAction) {
        when (action) {
            is FollowsAction.Retry -> loadData()
        }
    }

    private fun loadData() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            delay(1000)

            _uiState.update {
                it.copy(
                    isLoading = false,
                    followsUsers = FollowUser.PreviewFollowUserList
                )
            }
        }
    }
}