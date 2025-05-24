package ru.sobeninalex.domain.di

import org.koin.dsl.module
import ru.sobeninalex.domain.features.follows.usecase.FollowOrUnfollowUseCase
import ru.sobeninalex.domain.features.follows.usecase.GetFollowingSuggestionsUseCase
import ru.sobeninalex.domain.features.follows.usecase.GetFollowsUseCase

val FollowsDomainModule = module {
    factory { FollowOrUnfollowUseCase(repository = get()) }
    factory { GetFollowingSuggestionsUseCase(repository = get()) }
    factory { GetFollowsUseCase(repository = get()) }
}