package ru.sobeninalex.home.domain.usecase

import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.home.domain.FeatureHomeRepository

internal class GetFollowingSuggestionsUseCase(
    private val repository: FeatureHomeRepository
) {

    suspend operator fun invoke(): List<FollowUser> {
        return repository.getFollowingSuggestions()
    }
}