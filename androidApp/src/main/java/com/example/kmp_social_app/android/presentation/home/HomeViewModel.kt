package com.example.kmp_social_app.android.presentation.home

import com.example.kmp_social_app.android.common.utils.BaseViewModel
import com.example.kmp_social_app.android.common.utils.DefaultPagingManager
import com.example.kmp_social_app.android.common.utils.PagingManager
import com.example.kmp_social_app.common.utils.Constants
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import com.example.kmp_social_app.feature.follows.domain.usecase.FollowOrUnfollowUseCase
import com.example.kmp_social_app.feature.follows.domain.usecase.GetFollowingSuggestionsUseCase
import com.example.kmp_social_app.feature.post.domain.model.Post
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

    private val postsPagingManager by lazy { createPostsPagingManager() }

    init {
        loadContent()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.Refresh -> refreshContent()
            is HomeAction.OnBoardingFinishClick -> hideOnboarding()
            is HomeAction.LoadMorePosts -> loadMorePosts()
            is HomeAction.OnFollowButtonClick -> followUser(user = action.followedUser)
            is HomeAction.OnLikeClick -> TODO()
            is HomeAction.OnCommentClick -> TODO()
        }
    }

    private fun followUser(user: FollowUser) {
        viewModelScope.launch {
            runCatching {
                followOrUnfollowUseCase(followedUserId = user.id, shouldFollow = !user.isFollowing)
            }.onSuccess { isSuccess ->
                if (isSuccess) refreshContent()
            }.onFailure { error ->
                throw error
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
        postsPagingManager.reset()
        loadData()
    }

    private fun loadData() {
        if (uiState.value.showUsersRecommendation) {
            viewModelScope.launch {
                delay(1500) //todo: test
                runCatching {
                    getFollowingSuggestionsUseCase()
                }.onSuccess { response ->
                    _uiState.update { it.copy(users = response,) }
                }.onFailure { error ->
                    throw error
                }
            }
        }

        loadMorePosts()

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

//        viewModelScope.launch {
//            delay(2000) //todo: test
//            runCatching {
//                getFeedPostsUseCase(page = 0, pageSize = 10)
//            }.onSuccess { response ->
//                when (response) {
//                    is NetworkResponse.Failure -> {
//                        _uiState.update { it.copy(isLoading = false, isRefreshing = false) }
//                        showSnackbar(message = response.message)
//                    }
//
//                    is NetworkResponse.Success -> {
//                        _uiState.update {
//                            it.copy(
//                                isLoading = false,
//                                isRefreshing = false,
//                                posts = response.data
//                            )
//                        }
//                    }
//                }
//            }.onFailure { error ->
//                _uiState.update { it.copy(isLoading = false, isRefreshing = false) }
//                throw error
//            }
//        }

    }

    private fun loadMorePosts() {
        viewModelScope.launch {
            delay(3000) //todo: test
            postsPagingManager.loadItems()
        }
    }

    private fun createPostsPagingManager(): PagingManager<Post> {
        return DefaultPagingManager(
            onRequest = { page ->
                getFeedPostsUseCase(page = page, pageSize = Constants.DEFAULT_PAGE_SIZE)
            },
            onSuccess = { postsBucket, page ->
                val endReached = postsBucket.size < Constants.DEFAULT_PAGE_SIZE

                val posts = if (page == Constants.INITIAL_PAGE) {
                    postsBucket
                } else {
                    _uiState.value.posts + postsBucket
                }

                _uiState.update {
                    it.copy(
                        posts = posts,
                        endReached = endReached
                    )
                }
            },
            onFailure = { ex, page ->
                throw ex
            },
            onLoadStateChange = { isLoading ->
                _uiState.update { it.copy(isLoading = isLoading, isRefreshing = false) }
            }
        )
    }
}