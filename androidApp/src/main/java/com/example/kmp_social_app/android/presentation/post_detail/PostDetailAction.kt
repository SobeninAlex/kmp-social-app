package com.example.kmp_social_app.android.presentation.post_detail

sealed interface PostDetailAction {

    data object LoadMoreComments : PostDetailAction

    data class DeleteComment(val commentId: String) : PostDetailAction

    data object OnLikeClick : PostDetailAction

    data object OnAddCommentClick : PostDetailAction

    data object CloseBottomSheet : PostDetailAction

    data class OnSendCommentClick(val comment: String): PostDetailAction
}