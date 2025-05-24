package ru.sobeninalex.data.remote.features.account

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.domain.features.account.AccountRepository

internal val AccountFeatureModule = module {
    singleOf(::AccountRepositoryImpl).bind<AccountRepository>()
    factory { AccountApiService() }
}