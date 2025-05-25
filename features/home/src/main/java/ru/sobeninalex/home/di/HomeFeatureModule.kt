package ru.sobeninalex.home.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sobeninalex.home.presentation.create_post.CreatePostViewModel
import ru.sobeninalex.home.presentation.post_list.HomeViewModel

val HomeFeatureModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            getFollowingSuggestionsUseCase = get(),
            followOrUnfollowUseCase = get(),
            getFeedPostsUseCase = get(),
            likeOrUnlikeUseCase = get()
        )
    }

    viewModel<CreatePostViewModel> {
        CreatePostViewModel(
            createPostUseCase = get(),
            imageByteReader = get()
        )
    }
}