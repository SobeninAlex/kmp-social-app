package com.example.kmp_social_app.feature.follows.domain.usecase

import com.example.kmp_social_app.feature.follows.domain.FollowsRepository
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import com.example.kmp_social_app.feature.follows.domain.model.FollowsType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetFollowsUseCase : KoinComponent {

    private val repository by inject<FollowsRepository>()

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