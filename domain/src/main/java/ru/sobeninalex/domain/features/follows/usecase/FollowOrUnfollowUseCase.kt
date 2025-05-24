package ru.sobeninalex.domain.features.follows.usecase

import ru.sobeninalex.domain.features.follows.FollowsRepository

class FollowOrUnfollowUseCase(
    private val repository: FollowsRepository
) {

    suspend operator fun invoke(
        followedUserId: String,
        shouldFollow: Boolean
    ): Boolean {
        return repository.followOrUnfollow(
            followedUserId = followedUserId,
            shouldFollow = shouldFollow
        )
    }
}