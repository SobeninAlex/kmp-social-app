package ru.sobeninalex.account.domain.usecase

import ru.sobeninalex.account.domain.FeatureAccountRepository

internal class DeletePostUseCase(
    private val repository: FeatureAccountRepository
) {

    suspend operator fun invoke(
        postId: String
    ): Boolean {
        return repository.deletePost(postId)
    }
}