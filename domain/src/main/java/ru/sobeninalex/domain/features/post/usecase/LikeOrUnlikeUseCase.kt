package ru.sobeninalex.domain.features.post.usecase

import ru.sobeninalex.domain.features.post.PostRepository
import ru.sobeninalex.domain.features.post.model.Post

class LikeOrUnlikeUseCase(
    private val repository: PostRepository
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