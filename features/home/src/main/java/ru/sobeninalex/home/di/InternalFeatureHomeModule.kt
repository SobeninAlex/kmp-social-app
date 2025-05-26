package ru.sobeninalex.home.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sobeninalex.home.domain.usecase.CreatePostUseCase
import ru.sobeninalex.home.domain.usecase.FollowOrUnfollowUseCase
import ru.sobeninalex.home.domain.usecase.GetFeedPostsUseCase
import ru.sobeninalex.home.domain.usecase.GetFollowingSuggestionsUseCase
import ru.sobeninalex.home.domain.usecase.LikeOrUnlikeUseCase
import ru.sobeninalex.home.presentation.create_post.CreatePostViewModel
import ru.sobeninalex.home.presentation.post_list.HomeViewModel

val InternalFeatureHomeModule = module {
    viewModel<HomeViewModel> {
        HomeViewModel(
            getFollowingSuggestionsUseCase = get(),
            followOrUnfollowUseCase = get(),
            getFeedPostsUseCase = get(),
            likeOrUnlikeUseCase = get()
        )
    }
    factory { GetFollowingSuggestionsUseCase(repository = get()) }
    factory { FollowOrUnfollowUseCase(repository = get()) }
    factory { GetFeedPostsUseCase(repository = get()) }
    factory { LikeOrUnlikeUseCase(repository = get()) }

    viewModel<CreatePostViewModel> {
        CreatePostViewModel(
            createPostUseCase = get(),
            imageByteReader = get()
        )
    }
    factory { CreatePostUseCase(repository = get()) }
}