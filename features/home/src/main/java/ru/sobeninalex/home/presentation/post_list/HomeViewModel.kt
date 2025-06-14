package ru.sobeninalex.home.presentation.post_list

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.common.event.folllow.FollowStateChangedChannelEvent
import ru.sobeninalex.common.event.post.PostUpdatedChannelEvent
import ru.sobeninalex.common.event.profile.ProfileUpdatedSharedFlowEvent
import ru.sobeninalex.common.event.other.RefreshContentSharedFlowEvent
import ru.sobeninalex.common.event.post.PostDeletedSharedFlowEvent
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.common.presentation.BaseViewModel
import ru.sobeninalex.common.presentation.DefaultPagingManager
import ru.sobeninalex.common.presentation.PagingManager
import ru.sobeninalex.home.domain.usecase.DeletePostUseCase
import ru.sobeninalex.home.domain.usecase.FollowOrUnfollowUseCase
import ru.sobeninalex.home.domain.usecase.GetFeedPostsUseCase
import ru.sobeninalex.home.domain.usecase.GetFollowingSuggestionsUseCase
import ru.sobeninalex.home.domain.usecase.LikeOrUnlikeUseCase
import ru.sobeninalex.utils.helpers.Constants

internal class HomeViewModel(
    private val getFollowingSuggestionsUseCase: GetFollowingSuggestionsUseCase,
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase,
    private val getFeedPostsUseCase: GetFeedPostsUseCase,
    private val likeOrUnlikeUseCase: LikeOrUnlikeUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    private val postsPagingManager by lazy { createPostsPagingManager() }

    init {
        loadContent()

        PostUpdatedChannelEvent.event.onEach { post ->
            updatePost(post.postId) { post }
        }.launchIn(viewModelScope)

        FollowStateChangedChannelEvent.event.onEach {
            refreshContent()
        }.launchIn(viewModelScope)

        ProfileUpdatedSharedFlowEvent.event.onEach { profile ->
            updatePostsUser(profile)
        }.launchIn(viewModelScope)

        PostDeletedSharedFlowEvent.event.onEach { postId ->
            deletePostById(postId = postId)
        }.launchIn(viewModelScope)

        RefreshContentSharedFlowEvent.event.onEach {
            refreshContent()
        }.launchIn(viewModelScope)
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.Refresh -> refreshContent()
            is HomeAction.OnBoardingFinishClick -> hideOnboarding()
            is HomeAction.LoadMorePosts -> loadMorePosts()
            is HomeAction.OnFollowButtonClick -> followUser(user = action.followedUser)
            is HomeAction.OnLikeClick -> likeOrUnlike(post = action.post)
            is HomeAction.OnCommentClick -> {/*todo*/}
            is HomeAction.UpdatePost -> updatePost(action.post.postId) { action.post }
            is HomeAction.ShowDeletePostDialog -> showDeleteDialog(post = action.post)
            is HomeAction.HideDeletePostDialog -> hideDeleteDialog()
            is HomeAction.OnDeletePost -> deletePost(post = action.post)
            is HomeAction.OnRepeatClick -> loadContent()
        }
    }

    private fun deletePostById(postId: String) {
        val posts = _uiState.value.posts.filter { it.postId != postId }
        _uiState.update { state ->
            state.copy(
                posts = posts,
            )
        }
    }

    private fun deletePost(post: Post) {
        updatePost(post.postId) { it.copy(isDeletingPost = true) }
        viewModelScope.launch {
            delay(1500) //todo for test
            runCatching {
                deletePostUseCase(postId = post.postId)
            }.onSuccess {
                deletePostById(postId = post.postId)
            }.onFailure { error ->
                updatePost(post.postId) { it.copy(isDeletingPost = false) }
                throw error
            }
        }
    }

    private fun showDeleteDialog(post: Post) {
        _uiState.update { state ->
            state.copy(
                deletePostDialogState = DeletePostDialogState(
                    show = true,
                    post = post
                )
            )
        }
    }

    private fun hideDeleteDialog() {
        _uiState.update { state ->
            state.copy(
                deletePostDialogState = DeletePostDialogState(
                    show = false,
                )
            )
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
                        likesCount = it.likesCount.plus(operation),
                        enabledLike = true
                    )
                }
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

    private fun updatePostsUser(profile: Profile) {
        _uiState.update { state ->
            state.copy(
                posts = _uiState.value.posts.map {
                    if (it.userId == profile.id) {
                        it.copy(
                            userName = profile.name,
                            userAvatar = profile.avatar
                        )
                    } else {
                        it
                    }
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
        _uiState.update { it.copy(isLoading = true, throwable = null) }
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
                    _uiState.update { it.copy(users = response) }
                }.onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, throwable = error) }
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