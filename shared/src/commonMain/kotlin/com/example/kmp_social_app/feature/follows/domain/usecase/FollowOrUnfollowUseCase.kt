package com.example.kmp_social_app.feature.follows.domain.usecase

import com.example.kmp_social_app.common.utils.NetworkResponse
import com.example.kmp_social_app.feature.follows.domain.FollowsRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FollowOrUnfollowUseCase : KoinComponent {

    private val repository by inject<FollowsRepository>()

    suspend operator fun invoke(
        followedUserId: String,
        shouldFollow: Boolean
    ): NetworkResponse<Boolean> {
        return repository.followOrUnfollow(
            followedUserId = followedUserId,
            shouldFollow = shouldFollow
        )
    }
}