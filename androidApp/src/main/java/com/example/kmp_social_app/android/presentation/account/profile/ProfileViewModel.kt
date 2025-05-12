package com.example.kmp_social_app.android.presentation.account.profile

import com.example.kmp_social_app.android.common.utils.BaseViewModel
import com.example.kmp_social_app.android.common.utils.DefaultPagingManager
import com.example.kmp_social_app.android.common.utils.PagingManager
import com.example.kmp_social_app.common.utils.Constants
import com.example.kmp_social_app.feature.post.domain.model.Post
import com.example.kmp_social_app.feature.account.domain.model.Profile
import com.example.kmp_social_app.feature.account.domain.usecase.GetProfileUseCase
import com.example.kmp_social_app.feature.follows.domain.usecase.FollowOrUnfollowUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.GetPostsByUserIdUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userId: String,
    private val getProfileUseCase: GetProfileUseCase,
    private val getPostsByUserIdUseCase: GetPostsByUserIdUseCase,
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val postsPagingManager by lazy { createPostsPagingManager() }

    init {
        loadContent()
    }

    fun onAction(action: ProfileAction) = when (action) {
        is ProfileAction.LoadMorePosts -> loadMorePosts()
    }

    private fun loadContent() {
        _uiState.update { it.copy(isLoading = true) }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(1500) //todo: test
            getProfileUseCase(userId).onEach { profile ->
                _uiState.update { state ->
                    state.copy(
                        profile = profile
                    )
                }
            }.collect()

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
                getPostsByUserIdUseCase(
                    userId = userId,
                    page = page,
                    pageSize = Constants.DEFAULT_PAGE_SIZE
                )
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
                _uiState.update { it.copy(isLoading = isLoading) }
            }
        )
    }
}