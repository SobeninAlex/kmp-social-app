package ru.sobeninalex.home.domain.usecase

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.home.domain.FeatureHomeRepository

internal class GetFeedPostsUseCase(
    private val repository: FeatureHomeRepository
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