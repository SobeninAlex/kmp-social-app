package ru.sobeninalex.domain.features.post.usecase

import ru.sobeninalex.domain.features.post.PostRepository

class DeleteCommentUseCase(
    private val repository: PostRepository
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