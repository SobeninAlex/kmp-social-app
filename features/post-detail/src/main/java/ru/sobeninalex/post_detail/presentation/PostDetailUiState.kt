package ru.sobeninalex.post_detail.presentation

import androidx.compose.runtime.Immutable
import ru.sobeninalex.domain.features.post.model.Post
import ru.sobeninalex.domain.features.post.model.PostComment

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
    val type: Type = Type.ADD_COMMENT,
    val isOpen: Boolean = false
) {
    enum class Type {
        ADD_COMMENT
    }
}