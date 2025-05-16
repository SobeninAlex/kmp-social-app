package com.example.kmp_social_app.android.presentation.post_detail

import com.example.kmp_social_app.android.common.utils.BaseViewModel
import com.example.kmp_social_app.android.common.utils.DefaultPagingManager
import com.example.kmp_social_app.android.common.utils.PagingManager
import com.example.kmp_social_app.android.common.utils.event.PostUpdatedEvent
import com.example.kmp_social_app.common.utils.Constants
import com.example.kmp_social_app.feature.post.domain.model.Post
import com.example.kmp_social_app.feature.post.domain.model.PostComment
import com.example.kmp_social_app.feature.post.domain.usecase.AddCommentUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.GetPostCommentsUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.GetPostUseCase
import com.example.kmp_social_app.feature.post.domain.usecase.LikeOrUnlikeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postId: String,
    private val userId: String,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val likeOrUnlikeUseCase: LikeOrUnlikeUseCase,
    private val addCommentUseCase: AddCommentUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val postsPagingManager by lazy { createPostCommentsPagingManager() }

    init {
        loadContent()
    }

    fun onAction(action: PostDetailAction) {
        when (action) {
            is PostDetailAction.CreateComment -> TODO()
            is PostDetailAction.DeleteComment -> TODO()
            is PostDetailAction.LoadMoreComments -> loadMoreComments()
            is PostDetailAction.OnLikeClick -> likeOrUnlike()
            is PostDetailAction.OnAddCommentClick -> openBottomSheet(type = BottomSheetState.Type.AddComment)
            is PostDetailAction.CloseBottomSheet -> closeBottomSheet()
            is PostDetailAction.OnSendCommentClick -> {
                closeBottomSheet()
                sendComment(comment = action.comment)
            }
        }
    }

    private fun sendComment(comment: String) {
        viewModelScope.launch {
            runCatching {
                addCommentUseCase(
                    postId = postId,
                    content = comment
                )
            }.onSuccess { response ->
                updatePost {
                    val afterUpdate = it.copy(
                        isLiked = !it.isLiked,
                        likesCount = it.likesCount.plus(operation),
                        enabledLike = true
                    )
                    postUpdateEvent(afterUpdate)
                    afterUpdate
                }

                _uiState.update { state ->
                    val afterUpdate = state.copy(
                        comments = listOf(response) + _uiState.value.comments,
                        post = updatePost {
                            it.copy(
                                commentsCount = it.commentsCount + 1
                            )
                        }
                    )
                    afterUpdate
                }


                _uiState.update { state ->
                    val afterUpdate = state.copy(
                        comments = listOf(response) + _uiState.value.comments,
                        post = _uiState.value.post?.copy(
                            commentsCount = _uiState.value.post!!.commentsCount + 1
                        )
                    )
                    afterUpdate
                }


            }.onFailure { error ->
                throw error
            }
        }
    }

    private fun openBottomSheet(type: BottomSheetState.Type) {
        _uiState.update { state ->
            state.copy(
                bottomSheetState = _uiState.value.bottomSheetState.copy(
                    isOpen = true,
                    type = type
                )
            )
        }
    }

    private fun closeBottomSheet() {
        _uiState.update { state ->
            state.copy(
                bottomSheetState = _uiState.value.bottomSheetState.copy(
                    isOpen = false
                )
            )
        }
    }

    private fun likeOrUnlike() {
        val post = _uiState.value.post ?: return
        updatePost { it.copy(enabledLike = false) }
        viewModelScope.launch {
            val operation = if (post.isLiked) -1 else +1
            runCatching {
                likeOrUnlikeUseCase(post)
            }.onSuccess {
                updatePost {
                    val afterUpdate = it.copy(
                        isLiked = !it.isLiked,
                        likesCount = it.likesCount.plus(operation),
                        enabledLike = true
                    )
                    postUpdateEvent(afterUpdate)
                    afterUpdate
                }
            }.onFailure { error ->
                updatePost { it.copy(enabledLike = true) }
                throw error
            }
        }
    }

    private fun loadContent() {
        _uiState.update { it.copy(isLoading = true) }
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(1000)

            runCatching {
                getPostUseCase(postId = postId)
            }.onSuccess { response ->
                _uiState.update { state ->
                    state.copy(post = response)
                }
            }.onFailure { error ->
                throw error
            }

            loadMoreComments()
        }
    }

    private fun loadMoreComments() {
        viewModelScope.launch {
            postsPagingManager.loadItems()
        }
    }

    private fun createPostCommentsPagingManager(): PagingManager<PostComment> {
        return DefaultPagingManager(
            onRequest = { page ->
                delay(1500) //todo: test
                getPostCommentsUseCase(
                    postId = postId,
                    page = page,
                    pageSize = Constants.DEFAULT_PAGE_SIZE
                )
            },
            onSuccess = { items, page ->
                val endReached = items.size < Constants.DEFAULT_PAGE_SIZE

                val comments = if (page == Constants.INITIAL_PAGE) {
                    items
                } else {
                    _uiState.value.comments + items
                }

                _uiState.update {
                    it.copy(
                        comments = comments,
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

    private fun updatePost(update: (Post) -> Post) {
        val post = _uiState.value.post ?: return
        _uiState.update { state ->
            state.copy(
                post = update(post)
            )
        }
    }

    private fun postUpdateEvent(post: Post) {
        viewModelScope.launch {
            PostUpdatedEvent.sendEvent(post)
        }
    }
}