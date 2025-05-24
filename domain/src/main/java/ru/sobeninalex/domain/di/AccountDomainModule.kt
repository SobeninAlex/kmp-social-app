package ru.sobeninalex.domain.di

import org.koin.dsl.module
import ru.sobeninalex.domain.features.account.usecase.GetProfileUseCase
import ru.sobeninalex.domain.features.account.usecase.UpdateProfileUseCase

val AccountDomainModule = module {
    factory { GetProfileUseCase(repository = get()) }
    factory { UpdateProfileUseCase(repository = get()) }
}