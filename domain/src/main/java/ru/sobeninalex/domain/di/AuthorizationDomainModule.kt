package ru.sobeninalex.domain.di

import org.koin.dsl.module
import ru.sobeninalex.domain.features.authorization.usecase.LoginUseCase
import ru.sobeninalex.domain.features.authorization.usecase.SignUpUseCase

val AuthorizationDomainModule = module {
    factory { SignUpUseCase(repository = get()) }
    factory { LoginUseCase(repository = get()) }
}