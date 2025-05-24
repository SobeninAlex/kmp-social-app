package ru.sobeninalex.data.remote.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.data.remote.features.authorization.AuthApiService
import ru.sobeninalex.data.remote.features.authorization.AuthRepositoryImpl
import ru.sobeninalex.domain.features.authorization.AuthRepository

val AuthorizationDataModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    factory { AuthApiService() }
}