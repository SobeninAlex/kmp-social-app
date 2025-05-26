package ru.sobeninalex.account.presentation.profile

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.account.domain.usecase.FollowOrUnfollowUseCase
import ru.sobeninalex.account.domain.usecase.GetPostsByUserIdUseCase
import ru.sobeninalex.account.domain.usecase.GetProfileUseCase
import ru.sobeninalex.account.domain.usecase.LikeOrUnlikeUseCase
import ru.sobeninalex.common.event.FollowStateChangeEvent
import ru.sobeninalex.common.event.PostUpdateEvent
import ru.sobeninalex.common.event.ProfileUpdateEvent
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.common.presentation.BaseViewModel
import ru.sobeninalex.common.presentation.DefaultPagingManager
import ru.sobeninalex.common.presentation.PagingManager
import ru.sobeninalex.utils.helpers.Constants

internal class ProfileViewModel(
    private val userId: String,
    private val getProfileUseCase: GetProfileUseCase,
    private val getPostsByUserIdUseCase: GetPostsByUserIdUseCase,
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase,
    private val likeOrUnlikeUseCase: LikeOrUnlikeUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    private val profileFlow = getProfileUseCase(userId)
        .onStart {
            delay(1500) //todo: test
        }
        .onEach { profile ->
            _uiState.update { state ->
                state.copy(
                    profile = profile,
                    followingOperation = false
                )
            }
        }

    private val postsPagingManager by lazy { createPostsPagingManager() }

    init {
        loadContent()

        ProfileUpdateEvent.event
            .onEach { refreshContent() }
            .launchIn(viewModelScope)
    }

    fun onAction(action: ProfileAction) = when (action) {
        is ProfileAction.LoadMorePosts -> loadMorePosts()
        is ProfileAction.OnFollowButtonClick -> followUser(profile = action.profile)
        is ProfileAction.OnLikeClick -> likeOrUnlike(post = action.post)
        is ProfileAction.Refresh -> refreshContent()
    }

    private fun likeOrUnlike(post: Post) {
        updatePost(post.postId) { it.copy(enabledLike = false) }
        viewModelScope.launch {
            val operation = if (post.isLiked) -1 else +1
            runCatching {
                likeOrUnlikeUseCase(post)
            }.onSuccess { isSuccess ->
                updatePost(post.postId) {
                    val afterUpdate = it.copy(
                        isLiked = !it.isLiked,
                        likesCount = it.likesCount.plus(operation),
                        enabledLike = true
                    )
                    postUpdateEvent(afterUpdate)
                    afterUpdate
                }
            }.onFailure { error ->
                updatePost(post.postId) { it.copy(enabledLike = true) }
                throw error
            }
        }
    }

    private fun followUser(profile: Profile) {
        _uiState.update { it.copy(followingOperation = true) }
        viewModelScope.launch {
            runCatching {
                followOrUnfollowUseCase(
                    followedUserId = profile.id,
                    shouldFollow = !profile.isFollowing
                )
            }.onSuccess { isSuccess ->
                profileFlow.collect()
                FollowStateChangeEvent.sendEvent()
            }.onFailure { error ->
                throw error
            }
        }
    }

    private fun refreshContent() {
        _uiState.update { it.copy(isRefreshing = true, endReached = true) }
        postsPagingManager.reset()
        loadData()
    }

    private fun loadContent() {
        _uiState.update { it.copy(isLoading = true) }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            profileFlow.collect()
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
                if (_uiState.value.isRefreshing) {
                    _uiState.update { it.copy(isRefreshing = isLoading) }
                } else {
                    _uiState.update { it.copy(isLoading = isLoading) }
                }
            }
        )
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

    private fun postUpdateEvent(post: Post) {
        viewModelScope.launch {
            PostUpdateEvent.sendEvent(post)
        }
    }
}