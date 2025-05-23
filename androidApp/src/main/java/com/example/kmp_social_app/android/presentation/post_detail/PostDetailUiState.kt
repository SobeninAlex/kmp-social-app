package com.example.kmp_social_app.android.presentation.post_detail

import androidx.compose.runtime.Immutable
import com.example.kmp_social_app.feature.post.domain.model.Post
import com.example.kmp_social_app.feature.post.domain.model.PostComment

@Immutable
data class PostDetailUiState(
    val isLoading: Boolean = false,
    val post: Post? = null,
    val comments: List<PostComment> = emptyList(),
    val endReached: Boolean = true,
    val bottomSheetState: BottomSheetState = BottomSheetState(),
    val isAddingNewComment: Boolean = false,
) {

    companion object {
        val Preview = PostDetailUiState(
            isLoading = false,
            post = Post.Preview,
            comments = PostComment.PreviewList
        )
    }
}

@Immutable
data class BottomSheetState(
    val type: Type = Type.AddComment,
    val isOpen: Boolean = false
) {
    enum class Type {
        AddComment
    }
}