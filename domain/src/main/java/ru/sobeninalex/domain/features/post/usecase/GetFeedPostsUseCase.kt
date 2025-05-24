package ru.sobeninalex.domain.features.post.usecase

import ru.sobeninalex.domain.features.post.PostRepository
import ru.sobeninalex.domain.features.post.model.Post

class GetFeedPostsUseCase(
    private val repository: PostRepository
) {

    suspend operator fun invoke(
        page: Int,
        pageSize: Int
    ): List<Post> {
        return repository.getFeedPosts(
            page = page,
            pageSize = pageSize
        )
    }
}