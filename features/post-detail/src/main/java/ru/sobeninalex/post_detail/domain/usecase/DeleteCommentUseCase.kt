package ru.sobeninalex.post_detail.domain.usecase

import ru.sobeninalex.post_detail.domain.FeaturePostDetailRepository

internal class DeleteCommentUseCase(
    private val repository: FeaturePostDetailRepository
) {

    suspend operator fun invoke(
        commentId: String,
        postId: String,
    ) {
        return repository.deleteComment(
            commentId = commentId,
            postId = postId
        )
    }
}