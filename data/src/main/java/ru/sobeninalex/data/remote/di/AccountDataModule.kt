package ru.sobeninalex.data.remote.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.data.remote.features.account.AccountApiService
import ru.sobeninalex.data.remote.features.account.AccountRepositoryImpl
import ru.sobeninalex.domain.features.account.AccountRepository

val AccountDataModule = module {
    singleOf(::AccountRepositoryImpl).bind<AccountRepository>()
    factory { AccountApiService() }
}