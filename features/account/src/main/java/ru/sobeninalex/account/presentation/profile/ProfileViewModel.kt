package ru.sobeninalex.account.presentation.profile

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.account.domain.usecase.DeletePostUseCase
import ru.sobeninalex.account.domain.usecase.FollowOrUnfollowUseCase
import ru.sobeninalex.account.domain.usecase.GetPostsByUserIdUseCase
import ru.sobeninalex.account.domain.usecase.GetProfileUseCase
import ru.sobeninalex.account.domain.usecase.LikeOrUnlikeUseCase
import ru.sobeninalex.common.event.folllow.FollowStateChangedChannelEvent
import ru.sobeninalex.common.event.post.PostDeletedSharedFlowEvent
import ru.sobeninalex.common.event.post.PostUpdatedChannelEvent
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.common.presentation.BaseViewModel
import ru.sobeninalex.common.presentation.DefaultPagingManager
import ru.sobeninalex.common.presentation.PagingManager
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.mergeWith

internal class ProfileViewModel(
    private val userId: String,
    private val getProfileUseCase: GetProfileUseCase,
    private val getPostsByUserIdUseCase: GetPostsByUserIdUseCase,
    private val followOrUnfollowUseCase: FollowOrUnfollowUseCase,
    private val likeOrUnlikeUseCase: LikeOrUnlikeUseCase,
    private val deletePostUseCase: DeletePostUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    private val profile = getProfileUseCase(userId)
        .map { profile ->
            _uiState.value.copy(
                profile = profile,
                posts = _uiState.value.posts.map { post ->
                    post.copy(
                        userName = profile.name,
                        userAvatar = profile.avatar
                    )
                }
            )
        }
        .onEach { state ->
            _uiState.update { state }
        }

    val uiState = _uiState.mergeWith(profile)

    private val postsPagingManager by lazy { createPostsPagingManager() }

    init {
        loadMorePosts()
    }

    fun onAction(action: ProfileAction) = when (action) {
        is ProfileAction.LoadMorePosts -> loadMorePosts()
        is ProfileAction.OnFollowButtonClick -> followUser(profile = action.profile)
        is ProfileAction.OnLikeClick -> likeOrUnlike(post = action.post)
        is ProfileAction.HideDeletePostDialog -> hideDeleteDialog()
        is ProfileAction.ShowDeletePostDialog -> showDeleteDialog(post = action.post)
        is ProfileAction.OnDeletePost -> deletePost(post = action.post)
    }

    private fun deletePost(post: Post) {
        updatePost(post.postId) { it.copy(isDeletingPost = true) }
        viewModelScope.launch {
            delay(1500) //todo for test
            runCatching {
                deletePostUseCase(postId = post.postId)
            }.onSuccess {
                PostDeletedSharedFlowEvent.sendEvent(postId = post.postId)
                deletePostById(postId = post.postId)
            }.onFailure { error ->
                updatePost(post.postId) { it.copy(isDeletingPost = false) }
                throw error
            }
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
            delay(2000) //todo for test
            runCatching {
                followOrUnfollowUseCase(
                    followedUserId = profile.id,
                    shouldFollow = !profile.isFollowing
                )
            }.onSuccess { _ ->
                _uiState.update { it.copy(followingOperation = false) }
                updateProfile {
                    it.copy(
                        isFollowing = !profile.isFollowing
                    )
                }
                FollowStateChangedChannelEvent.sendEvent()
            }.onFailure { error ->
                throw error
            }
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

    private fun updateProfile(update: (Profile) -> Profile) {
        val profile = _uiState.value.profile ?: return
        _uiState.update { state ->
            state.copy(
                profile = update(profile)
            )
        }
    }

    private fun postUpdateEvent(post: Post) {
        viewModelScope.launch {
            PostUpdatedChannelEvent.sendEvent(post)
        }
    }
}