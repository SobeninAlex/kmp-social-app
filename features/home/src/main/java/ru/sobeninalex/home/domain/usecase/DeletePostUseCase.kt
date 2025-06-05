package ru.sobeninalex.home.domain.usecase

import ru.sobeninalex.home.domain.FeatureHomeRepository

internal class DeletePostUseCase(
    private val repository: FeatureHomeRepository
) {

    suspend operator fun invoke(
        postId: String
    ): Boolean {
        return repository.deletePost(postId)
    }
}