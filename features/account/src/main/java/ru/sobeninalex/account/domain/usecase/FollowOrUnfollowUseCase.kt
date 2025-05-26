package ru.sobeninalex.account.domain.usecase

import ru.sobeninalex.account.domain.FeatureAccountRepository

internal class FollowOrUnfollowUseCase(
    private val repository: FeatureAccountRepository
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