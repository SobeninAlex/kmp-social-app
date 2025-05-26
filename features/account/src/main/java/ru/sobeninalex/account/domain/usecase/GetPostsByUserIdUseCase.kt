package ru.sobeninalex.account.domain.usecase

import ru.sobeninalex.account.domain.FeatureAccountRepository
import ru.sobeninalex.common.models.post.Post

internal class GetPostsByUserIdUseCase(
    private val repository: FeatureAccountRepository
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