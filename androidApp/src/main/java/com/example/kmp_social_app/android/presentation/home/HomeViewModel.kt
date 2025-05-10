package com.example.kmp_social_app.android.presentation.home

import androidx.lifecycle.viewModelScope
import com.example.kmp_social_app.android.common.utils.BaseViewModel
import com.example.kmp_social_app.common.utils.NetworkResponse
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import com.example.kmp_social_app.feature.follows.domain.usecase.FollowOrUnfollowUseCase
import com.example.kmp_social_app.feature.follows.domain.usecase.GetFollowingSuggestionsUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.GetFeedPostsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getFollowingSuggestionsUseCase: GetFollowingSuggestionsUseCase,
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase,
    private val getFeedPostsUseCase: GetFeedPostsUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadContent()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.Refresh -> refreshContent()
            is HomeAction.OnBoardingFinishClick -> hideOnboarding()
            is HomeAction.LoadMorePosts -> TODO()
            is HomeAction.OnFollowButtonClick -> followUser(user = action.followedUser)
            is HomeAction.OnLikeClick -> TODO()
            is HomeAction.OnCommentClick -> TODO()
        }
    }

    private fun followUser(user: FollowUser) {
        viewModelScope.launch {
            runCatching {
                followOrUnfollowUseCase(followedUserId = user.id, shouldFollow = !user.isFollowing)
            }.onSuccess { response ->
                when (response) {
                    is NetworkResponse.Failure -> {
                        showSnackbar(message = response.message)
                    }
                    is NetworkResponse.Success -> {
                        refreshContent()
                    }
                }

            }.onFailure { error ->
                showSnackbar(message = error.message)
            }
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
        if (uiState.value.showUsersRecommendation) {
            viewModelScope.launch {
                delay(1000) //todo: test
                runCatching {
                    getFollowingSuggestionsUseCase()
                }.onSuccess { response ->
                    when (response) {
                        is NetworkResponse.Failure -> {
                            showSnackbar(message = response.message)
                        }

                        is NetworkResponse.Success -> {
                            _uiState.update {
                                it.copy(
                                    users = response.data,
                                )
                            }
                        }
                    }
                }.onFailure { error ->
                    showSnackbar(message = error.message)
                }
            }
        }

//        //todo: test
//        viewModelScope.launch {
//            delay(1000)
//            _uiState.update {
//                it.copy(
//                    isLoading = false,
//                    isRefreshing = false,
//                    posts = Post.PreviewPostList
//                )
//            }
//        }

        viewModelScope.launch {
            delay(2000) //todo: test
            runCatching {
                getFeedPostsUseCase(page = 0, pageSize = 10)
            }.onSuccess { response ->
                when (response) {
                    is NetworkResponse.Failure -> {
                        _uiState.update { it.copy(isLoading = false, isRefreshing = false) }
                        showSnackbar(message = response.message)
                    }

                    is NetworkResponse.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isRefreshing = false,
                                posts = response.data
                            )
                        }
                    }
                }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, isRefreshing = false) }
                showSnackbar(message = error.message)
            }
        }
    }
}