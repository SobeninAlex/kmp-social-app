package ru.sobeninalex.authorization.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sobeninalex.authorization.presentation.login.LoginViewModel
import ru.sobeninalex.authorization.presentation.signup.SignUpViewModel

val AuthorizationFeatureModule = module {
    viewModel {
        SignUpViewModel(
            signUpUseCase = get(),
        )
    }

    viewModel {
        LoginViewModel(
            loginUseCase = get(),
        )
    }
}