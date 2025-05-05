package com.example.kmp_social_app.android.presentation.post_detail

import com.example.kmp_social_app.android.utils.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PostDetailViewModel : BaseViewModel() {

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState = _uiState.asStateFlow()


}