package ru.sobeninalex.domain.features.follows.usecase

import org.koin.core.component.KoinComponent
import ru.sobeninalex.domain.features.follows.FollowsRepository
import ru.sobeninalex.domain.features.follows.model.FollowUser
import ru.sobeninalex.domain.features.follows.model.FollowsType

class GetFollowsUseCase(
    private val repository: FollowsRepository
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