package com.example.kmp_social_app.di

import com.example.kmp_social_app.feature.auth.data.AuthRepositoryImpl
import com.example.kmp_social_app.feature.auth.data.AuthApiService
import com.example.kmp_social_app.feature.auth.domain.AuthRepository
import com.example.kmp_social_app.feature.auth.domain.usecase.SignInUseCase
import com.example.kmp_social_app.feature.auth.domain.usecase.SignUpUseCase
import com.example.kmp_social_app.common.utils.DispatcherProvider
import com.example.kmp_social_app.common.utils.provideDispatcher
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

private val authModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    factory { AuthApiService() }
    factory { SignUpUseCase() }
    factory { SignInUseCase() }
}

private val utilsModule = module {
    factory<DispatcherProvider> { provideDispatcher() }
}

fun getSharedModules() = listOf(
    platformModule,
    authModule,
    utilsModule
)