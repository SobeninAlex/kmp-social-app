package com.example.kmp_social_app.android.presentation.post_detail

import androidx.lifecycle.viewModelScope
import com.example.kmp_social_app.android.utils.BaseViewModel
import com.example.kmp_social_app.feature.comments.domain.model.Comment
import com.example.kmp_social_app.feature.post.domain.model.Post
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostDetailViewModel(
    private val postId: String,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDate()
    }

    fun onEvent(event: PostDetailEvent) {
        when (event) {
            is PostDetailEvent.Retry -> loadDate()
        }
    }

    private fun loadDate() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        updateCommentsState { it.copy(isLoading = true) }

        viewModelScope.launch {
            delay(1000)

            _uiState.update {
                it.copy(isLoading = false, post = Post.Preview)
            }

            delay(1000)

            updateCommentsState {
                it.copy(
                    isLoading = false,
                    comments = Comment.PreviewCommentList
                )
            }
        }
    }

    private fun updateCommentsState(update: (CommentsState) -> CommentsState) {
        _uiState.update { oldState ->
            oldState.copy(
                commentsState = update(oldState.commentsState)
            )
        }
    }
}