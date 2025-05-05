package com.example.kmp_social_app.android.presentation.post_detail

import androidx.compose.runtime.Immutable
import com.example.kmp_social_app.feature.comments.domain.model.Comment
import com.example.kmp_social_app.feature.post.domain.model.Post

@Immutable
data class PostDetailUiState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val commentsState: CommentsState = CommentsState(),
    val errorMessage: String? = null
) {

    companion object {
        val Preview = PostDetailUiState(
            isLoading = false,
            post = Post.Preview,
            commentsState = CommentsState.Preview
        )
    }
}

@Immutable
data class CommentsState(
    val isLoading: Boolean = false,
    val comments: List<Comment> = emptyList(),
) {

    companion object {
        val Preview = CommentsState(
            isLoading = false,
            comments = Comment.PreviewCommentList
        )
    }
}