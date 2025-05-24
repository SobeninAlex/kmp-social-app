package com.example.kmp_social_app.di

import ru.sobeninalex.account.presentation.edit.EditProfileViewModel
import ru.sobeninalex.account.presentation.follows.FollowsViewModel
import ru.sobeninalex.account.presentation.profile.ProfileViewModel
import ru.sobeninalex.home.presentation.HomeViewModel
import com.example.kmp_social_app.presentation.main.MainViewModel
import com.example.kmp_social_app.presentation.post_detail.PostDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::MainViewModel)

    viewModel { (postId: String) ->
        PostDetailViewModel(
            postId = postId,
            getPostCommentsUseCase = get(),
            getPostUseCase = get(),
            likeOrUnlikeUseCase = get(),
            addCommentUseCase = get(),
            deleteCommentUseCase = get(),
        )
    }

    viewModel { (userId: String) ->
        ru.sobeninalex.account.presentation.profile.ProfileViewModel(
            userId = userId,
            getProfileUseCase = get(),
            getPostsByUserIdUseCase = get(),
            followOrUnfollowUseCase = get(),
            likeOrUnlikeUseCase = get(),
        )
    }

    viewModel { (args: ru.sobeninalex.utils.navigation.args.EditProfileArgs) ->
        ru.sobeninalex.account.presentation.edit.EditProfileViewModel(args = args)
    }

    viewModel { (args: ru.sobeninalex.utils.navigation.args.FollowsArgs) ->
        ru.sobeninalex.account.presentation.follows.FollowsViewModel(
            args = args,
            getFollowsUseCase = get()
        )
    }
}