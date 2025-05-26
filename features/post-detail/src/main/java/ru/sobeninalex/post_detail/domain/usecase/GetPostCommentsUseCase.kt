package ru.sobeninalex.post_detail.domain.usecase

import ru.sobeninalex.common.models.post.PostComment
import ru.sobeninalex.post_detail.domain.FeaturePostDetailRepository

internal class GetPostCommentsUseCase(
    private val repository: FeaturePostDetailRepository
) {

    suspend operator fun invoke(
        postId: String,
        page: Int,
        pageSize: Int
    ) : List<PostComment> {
        return repository.getPostComments(
            postId = postId,
            page = page,
            pageSize = pageSize
        )
    }
}