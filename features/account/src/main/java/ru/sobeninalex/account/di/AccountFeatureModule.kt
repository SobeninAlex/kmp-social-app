package ru.sobeninalex.account.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sobeninalex.account.presentation.edit.EditProfileViewModel
import ru.sobeninalex.account.presentation.follows.FollowsViewModel
import ru.sobeninalex.account.presentation.profile.ProfileViewModel
import ru.sobeninalex.common.navigation.args.EditProfileArgs
import ru.sobeninalex.common.navigation.args.FollowsArgs

val AccountFeatureModule = module {
    viewModel { (userId: String) ->
        ProfileViewModel(
            userId = userId,
            getProfileUseCase = get(),
            getPostsByUserIdUseCase = get(),
            followOrUnfollowUseCase = get(),
            likeOrUnlikeUseCase = get(),
        )
    }

    viewModel { (args: EditProfileArgs) ->
        EditProfileViewModel(
            args = args,
            updateProfileUseCase = get(),
            imageByteReader = get(),
        )
    }

    viewModel { (args: FollowsArgs) ->
        FollowsViewModel(
            args = args,
            getFollowsUseCase = get()
        )
    }
}