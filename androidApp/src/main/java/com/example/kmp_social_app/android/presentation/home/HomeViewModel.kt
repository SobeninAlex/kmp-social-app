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
import com.example.kmp_social_app.feature.post.domain.usecase.LikeOrUnlikeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getFollowingSuggestionsUseCase: GetFollowingSuggestionsUseCase,
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase,
    private val getFeedPostsUseCase: GetFeedPostsUseCase,
    private val likeOrUnlikeUseCase: LikeOrUnlikeUseCase,
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
            is HomeAction.OnLikeClick -> likeOrUnlike(post = action.post)
            is HomeAction.OnCommentClick -> TODO()
        }
    }

    private fun likeOrUnlike(post: Post) {
        updatePost(post.postId) { it.copy(enabledLike = false) }
        viewModelScope.launch {
            val operation = if (post.isLiked) -1 else +1
            runCatching {
                likeOrUnlikeUseCase(post)
            }.onSuccess {
                updatePost(post.postId) {
                    it.copy(
                        isLiked = !it.isLiked,
                        likesCount = it.likesCount.plus(operation)
                    )
                }
                updatePost(post.postId) { it.copy(enabledLike = true) }
            }.onFailure { error ->
                updatePost(post.postId) { it.copy(enabledLike = true) }
                throw error
            }
        }
    }

    private fun updatePost(postId: String, update: (Post) -> Post) {
        _uiState.update { state ->
            state.copy(
                posts = state.posts.map {
                    if (it.postId == postId) {
                        update(it)
                    } else it
                }
            )
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
        _uiState.update { it.copy(isRefreshing = true, endReached = true) }
        postsPagingManager.reset()
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            if (uiState.value.showUsersRecommendation) {
                delay(1500) //todo: test
                runCatching {
                    getFollowingSuggestionsUseCase()
                }.onSuccess { response ->
                    _uiState.update { it.copy(users = response,) }
                }.onFailure { error ->
                    throw error
                }
            }

            loadMorePosts()
        }
    }

    private fun loadMorePosts() {
        viewModelScope.launch {
            postsPagingManager.loadItems()
        }
    }

    private fun createPostsPagingManager(): PagingManager<Post> {
        return DefaultPagingManager(
            onRequest = { page ->
                delay(1500) //todo: test
                getFeedPostsUseCase(page = page, pageSize = Constants.DEFAULT_PAGE_SIZE)
            },
            onSuccess = { items, page ->
                val endReached = items.size < Constants.DEFAULT_PAGE_SIZE

                val posts = if (page == Constants.INITIAL_PAGE) {
                    items
                } else {
                    _uiState.value.posts + items
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
                if (_uiState.value.isRefreshing) {
                    _uiState.update { it.copy(isRefreshing = isLoading) }
                } else {
                    _uiState.update { it.copy(isLoading = isLoading) }
                }
            }
        )
    }
}