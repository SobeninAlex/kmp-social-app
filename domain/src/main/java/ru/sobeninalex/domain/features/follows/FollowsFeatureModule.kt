package ru.sobeninalex.domain.features.follows

import org.koin.dsl.module
import ru.sobeninalex.domain.features.follows.usecase.FollowOrUnfollowUseCase
import ru.sobeninalex.domain.features.follows.usecase.GetFollowingSuggestionsUseCase
import ru.sobeninalex.domain.features.follows.usecase.GetFollowsUseCase

internal val FollowsFeatureModule = module {
    factory {
        FollowOrUnfollowUseCase(repository = get())
    }

    factory {
        GetFollowingSuggestionsUseCase(repository = get())
    }

    factory {
        GetFollowsUseCase(repository = get())
    }
}