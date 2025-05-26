package ru.sobeninalex.account.domain.usecase

import ru.sobeninalex.account.domain.FeatureAccountRepository
import ru.sobeninalex.common.models.post.Post

internal class LikeOrUnlikeUseCase(
    private val repository: FeatureAccountRepository
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