package ru.sobeninalex.home.domain.usecase

import ru.sobeninalex.home.domain.FeatureHomeRepository

internal class FollowOrUnfollowUseCase(
    private val repository: FeatureHomeRepository
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