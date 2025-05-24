package ru.sobeninalex.domain.features.post.usecase

import ru.sobeninalex.domain.features.post.PostRepository
import ru.sobeninalex.domain.features.post.model.Post

class GetPostsByUserIdUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        userId: String,
        page: Int,
        pageSize: Int
    ): List<Post> {
        return repository.getPostsByUserId(
            userId = userId,
            page = page,
            pageSize = pageSize
        )
    }
}