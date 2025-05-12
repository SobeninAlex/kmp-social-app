package com.example.kmp_social_app.android.di

import com.example.kmp_social_app.android.presentation.account.edit.EditProfileArgs
import com.example.kmp_social_app.android.presentation.account.edit.EditProfileViewModel
import com.example.kmp_social_app.android.presentation.account.follows.FollowsArgs
import com.example.kmp_social_app.android.presentation.account.follows.FollowsViewModel
import com.example.kmp_social_app.android.presentation.account.profile.ProfileViewModel
import com.example.kmp_social_app.android.presentation.auth.login.LoginViewModel
import com.example.kmp_social_app.android.presentation.auth.signup.SignUpViewModel
import com.example.kmp_social_app.android.presentation.home.HomeViewModel
import com.example.kmp_social_app.android.presentation.main.MainViewModel
import com.example.kmp_social_app.android.presentation.post_detail.PostDetailViewModel
import com.example.kmp_social_app.android.common.utils.CoreProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModel {
        SignUpViewModel(
            signUpUseCase = get(),
        )
    }

    viewModelOf(::LoginViewModel)

    viewModelOf(::HomeViewModel)

    viewModelOf(::MainViewModel)

    viewModel { (postId: String) ->
        PostDetailViewModel(postId = postId)
    }

    viewModel { (userId: String) ->
        ProfileViewModel(
            userId = userId,
            getProfileUseCase = get(),
            getPostsByUserIdUseCase = get(),
            followOrUnfollowUseCase = get()
        )
    }

    viewModel { (args: EditProfileArgs) ->
        EditProfileViewModel(args = args)
    }

    viewModel { (args: FollowsArgs) ->
        FollowsViewModel(args = args)
    }

    single<CoreProvider> {
        CoreProvider(androidContext())
    }
}