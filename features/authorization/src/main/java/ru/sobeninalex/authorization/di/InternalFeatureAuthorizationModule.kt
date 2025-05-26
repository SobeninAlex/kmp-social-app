package ru.sobeninalex.authorization.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.sobeninalex.authorization.domain.usecase.LoginUseCase
import ru.sobeninalex.authorization.domain.usecase.SignUpUseCase
import ru.sobeninalex.authorization.presentation.login.LoginViewModel
import ru.sobeninalex.authorization.presentation.signup.SignUpViewModel

val InternalFeatureAuthorizationModule = module {
    viewModel {
        SignUpViewModel(
            signUpUseCase = get(),
        )
    }
    factory { SignUpUseCase(repository = get()) }

    viewModel {
        LoginViewModel(
            loginUseCase = get(),
        )
    }
    factory { LoginUseCase(repository = get()) }
}