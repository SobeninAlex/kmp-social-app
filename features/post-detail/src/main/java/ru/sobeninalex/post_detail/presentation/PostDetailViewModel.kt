package ru.sobeninalex.post_detail.presentation

import ru.sobeninalex.common.event.PostUpdateEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.sobeninalex.domain.features.post.model.Post
import ru.sobeninalex.domain.features.post.model.PostComment
import ru.sobeninalex.domain.features.post.usecase.AddCommentUseCase
import ru.sobeninalex.domain.features.post.usecase.DeleteCommentUseCase
import ru.sobeninalex.domain.features.post.usecase.GetPostCommentsUseCase
import ru.sobeninalex.domain.features.post.usecase.GetPostUseCase
import ru.sobeninalex.domain.features.post.usecase.LikeOrUnlikeUseCase
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.common.presentation.BaseViewModel
import ru.sobeninalex.common.presentation.DefaultPagingManager
import ru.sobeninalex.common.presentation.PagingManager

class PostDetailViewModel(
    private val postId: String,
    private val getPostCommentsUseCase: GetPostCommentsUseCase,
    private val getPostUseCase: GetPostUseCase,
    private val likeOrUnlikeUseCase: LikeOrUnlikeUseCase,
    private val addCommentUseCase: AddCommentUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val postsPagingManager by lazy { createPostCommentsPagingManager() }

    init {
        loadContent()
    }

    fun onAction(action: PostDetailAction) {
        when (action) {
            is PostDetailAction.DeleteComment -> deleteComment(commentId = action.commentId)
            is PostDetailAction.LoadMoreComments -> loadMoreComments()
            is PostDetailAction.OnLikeClick -> likeOrUnlike()
            is PostDetailAction.OnAddCommentClick -> openBottomSheet(type = BottomSheetState.Type.ADD_COMMENT)
            is PostDetailAction.CloseBottomSheet -> closeBottomSheet()
            is PostDetailAction.OnSendCommentClick -> sendComment(comment = action.comment)
        }
    }

    private fun deleteComment(commentId: String) {
        viewModelScope.launch {
            updateComment(commentId) {
                it.copy(isDeletingComment = true)
            }

            runCatching {
                delay(1500) //todo: test
                deleteCommentUseCase(
                    commentId = commentId,
                    postId = postId
                )
            }.onSuccess {
                _uiState.update { state ->
                    updateComment(commentId) {
                        it.copy(isDeletingComment = false)
                    }

                    state.copy(
                        comments = _uiState.value.comments.minusElement(_uiState.value.comments.find { it.commentId == commentId }!!),
                        post = _uiState.value.post?.copy(
                            commentsCount = _uiState.value.post!!.commentsCount - 1
                        )
                    )
                }
                postUpdateEvent(_uiState.value.post!!)
            }.onFailure { error ->
                updateComment(commentId) {
                    it.copy(isDeletingComment = false)
                }
                throw error
            }
        }
    }

    private fun sendComment(comment: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(isAddingNewComment = true)
            }

            runCatching {
                delay(1500) //todo: test
                addCommentUseCase(
                    postId = postId,
                    content = comment
                )
            }.onSuccess { response ->
                _uiState.update { state ->
                    val afterUpdate = state.copy(
                        isAddingNewComment = false,
                        comments = listOf(response) + _uiState.value.comments,
                        post = _uiState.value.post?.copy(
                            commentsCount = _uiState.value.post!!.commentsCount + 1
                        )
                    )
                    closeBottomSheet()
                    postUpdateEvent(afterUpdate.post!!)
                    afterUpdate
                }
            }.onFailure { error ->
                _uiState.update { it.copy(isAddingNewComment = false) }
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

    private fun updateComment(commentId: String, update: (PostComment) -> PostComment) {
        _uiState.update { state ->
            state.copy(
                comments = state.comments.map {
                    if (it.commentId == commentId) {
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