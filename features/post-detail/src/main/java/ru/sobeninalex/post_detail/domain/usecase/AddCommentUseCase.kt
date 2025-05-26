package ru.sobeninalex.post_detail.domain.usecase

import ru.sobeninalex.common.models.post.PostComment
import ru.sobeninalex.post_detail.domain.FeaturePostDetailRepository

internal class AddCommentUseCase(
    private val repository: FeaturePostDetailRepository
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