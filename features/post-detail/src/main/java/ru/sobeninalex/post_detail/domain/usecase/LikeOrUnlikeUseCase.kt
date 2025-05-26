package ru.sobeninalex.post_detail.domain.usecase

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.post_detail.domain.FeaturePostDetailRepository

internal class LikeOrUnlikeUseCase(
    private val repository: FeaturePostDetailRepository
) {

    suspend operator fun invoke(
        post: Post
    ): Boolean {
        return repository.likeOrUnlikePost(
            postId = post.postId,
            shouldLike = !post.isLiked
        )
    }
}