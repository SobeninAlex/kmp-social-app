package ru.sobeninalex.account.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sobeninalex.account.domain.usecase.FollowOrUnfollowUseCase
import ru.sobeninalex.account.domain.usecase.GetFollowsUseCase
import ru.sobeninalex.account.domain.usecase.GetPostsByUserIdUseCase
import ru.sobeninalex.account.domain.usecase.GetProfileUseCase
import ru.sobeninalex.account.domain.usecase.LikeOrUnlikeUseCase
import ru.sobeninalex.account.domain.usecase.UpdateProfileUseCase
import ru.sobeninalex.account.presentation.edit.EditProfileViewModel
import ru.sobeninalex.account.presentation.follows.FollowsViewModel
import ru.sobeninalex.account.presentation.profile.ProfileViewModel
import ru.sobeninalex.common.navigation.args.EditProfileArgs
import ru.sobeninalex.common.navigation.args.FollowsArgs

val InternalFeatureAccountModule = module {
    viewModel { (userId: String) ->
        ProfileViewModel(
            userId = userId,
            getProfileUseCase = get(),
            getPostsByUserIdUseCase = get(),
            followOrUnfollowUseCase = get(),
            likeOrUnlikeUseCase = get(),
        )
    }
    factory { GetProfileUseCase(repository = get()) }
    factory { GetPostsByUserIdUseCase(repository = get()) }
    factory { FollowOrUnfollowUseCase(repository = get()) }
    factory { LikeOrUnlikeUseCase(repository = get()) }

    viewModel { (args: EditProfileArgs) ->
        EditProfileViewModel(
            args = args,
            updateProfileUseCase = get(),
            imageByteReader = get(),
        )
    }
    factory { UpdateProfileUseCase(repository = get()) }

    viewModel { (args: FollowsArgs) ->
        FollowsViewModel(
            args = args,
            getFollowsUseCase = get()
        )
    }
    factory { GetFollowsUseCase(repository = get()) }
}