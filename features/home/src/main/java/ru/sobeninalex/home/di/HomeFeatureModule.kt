package ru.sobeninalex.home.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sobeninalex.home.presentation.HomeViewModel

val HomeFeatureModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            getFollowingSuggestionsUseCase = get(),
            followOrUnfollowUseCase = get(),
            getFeedPostsUseCase = get(),
            likeOrUnlikeUseCase = get()
        )
    }
}