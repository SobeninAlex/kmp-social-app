package ru.sobeninalex.domain.features.post.usecase

import ru.sobeninalex.domain.features.post.PostRepository
import ru.sobeninalex.domain.features.post.model.Post

class GetPostUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        postId: String,
    ): Post {
        return repository.getPost(postId = postId)
    }
}