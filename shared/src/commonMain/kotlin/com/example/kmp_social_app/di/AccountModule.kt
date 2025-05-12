package com.example.kmp_social_app.di

import com.example.kmp_social_app.feature.account.data.AccountApiService
import com.example.kmp_social_app.feature.account.data.AccountRepositoryImpl
import com.example.kmp_social_app.feature.account.domain.AccountRepository
import com.example.kmp_social_app.feature.account.domain.usecase.GetProfileUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val accountModule = module {
    singleOf(::AccountRepositoryImpl).bind<AccountRepository>()
    factory { AccountApiService() }
    factory { GetProfileUseCase() }
}