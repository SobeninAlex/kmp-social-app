package com.example.kmp_social_app.di

import com.example.kmp_social_app.feature.follows.data.FollowsApiService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.example.kmp_social_app.feature.follows.data.FollowsRepositoryImpl
import com.example.kmp_social_app.feature.follows.domain.FollowsRepository
import com.example.kmp_social_app.feature.follows.domain.usecase.FollowOrUnfollowUseCase
import com.example.kmp_social_app.feature.follows.domain.usecase.GetFollowingSuggestionsUseCase
import com.example.kmp_social_app.feature.follows.domain.usecase.GetFollowsUseCase
import org.koin.dsl.bind

internal val followsModule = module {
    singleOf(::FollowsRepositoryImpl).bind<FollowsRepository>()
    factory { FollowsApiService() }
    factory { FollowOrUnfollowUseCase() }
    factory { GetFollowingSuggestionsUseCase() }
    factory { GetFollowsUseCase() }
}