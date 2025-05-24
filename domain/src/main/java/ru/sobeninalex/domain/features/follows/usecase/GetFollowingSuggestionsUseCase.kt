package ru.sobeninalex.domain.features.follows.usecase

import ru.sobeninalex.domain.features.follows.FollowsRepository
import ru.sobeninalex.domain.features.follows.model.FollowUser

class GetFollowingSuggestionsUseCase(
    private val repository: FollowsRepository
) {

    suspend operator fun invoke(): List<FollowUser> {
        return repository.getFollowingSuggestions()
    }
}