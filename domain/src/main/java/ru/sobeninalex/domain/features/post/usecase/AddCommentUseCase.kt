package ru.sobeninalex.domain.features.post.usecase

import ru.sobeninalex.domain.features.post.PostRepository
import ru.sobeninalex.domain.features.post.model.PostComment

class AddCommentUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        postId: String,
        content: String
    ): PostComment {
        return repository.addComment(
            postId = postId,
            content = content
        )
    }
}