package com.example.kmp_social_app.feature.follows.domain.usecase

import com.example.kmp_social_app.common.utils.NetworkResponse
import com.example.kmp_social_app.feature.follows.domain.FollowsRepository
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetFollowingSuggestionsUseCase : KoinComponent {

    private val repository by inject<FollowsRepository>()

    suspend operator fun invoke(): NetworkResponse<List<FollowUser>> {
        return repository.getFollowingSuggestions()
    }
}