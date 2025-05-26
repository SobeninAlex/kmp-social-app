package ru.sobeninalex.account.domain.usecase

import org.koin.core.component.KoinComponent
import ru.sobeninalex.account.domain.FeatureAccountRepository
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.follow.FollowsType

internal class GetFollowsUseCase(
    private val repository: FeatureAccountRepository
) : KoinComponent {

    suspend operator fun invoke(
        followType: FollowsType,
        userId: String,
        page: Int,
        pageSize: Int
    ): List<FollowUser> {
        return when (followType) {
            FollowsType.FOLLOWERS -> {
                repository.getFollowers(
                    userId = userId,
                    page = page,
                    pageSize = pageSize
                )
            }

            FollowsType.FOLLOWING -> {
                repository.getFollowing(
                    userId = userId,
                    page = page,
                    pageSize = pageSize
                )
            }
        }
    }
}