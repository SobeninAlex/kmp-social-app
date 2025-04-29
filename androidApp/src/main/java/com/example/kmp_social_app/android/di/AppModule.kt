package com.example.kmp_social_app.android.di

import com.example.kmp_social_app.android.presentation.auth.login.LoginViewModel
import com.example.kmp_social_app.android.presentation.auth.signup.SignUpViewModel
import com.example.kmp_social_app.android.presentation.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModel {
        SignUpViewModel(
            signUpUseCase = get()
        )
    }
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
}