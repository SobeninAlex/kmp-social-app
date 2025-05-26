package ru.sobeninalex.home.domain.usecase

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.home.domain.FeatureHomeRepository

internal class LikeOrUnlikeUseCase(
    private val repository: FeatureHomeRepository
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