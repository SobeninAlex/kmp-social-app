package ru.sobeninalex.post_detail.domain.usecase

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.post_detail.domain.FeaturePostDetailRepository

internal class GetPostUseCase(
    private val repository: FeaturePostDetailRepository
) {

    suspend operator fun invoke(
        postId: String,
    ): Post {
        return repository.getPost(postId = postId)
    }
}