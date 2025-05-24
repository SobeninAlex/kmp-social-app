package ru.sobeninalex.data.remote.features.authorization

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.data.remote.features.account.AccountApiService
import ru.sobeninalex.domain.features.account.AccountRepository
import ru.sobeninalex.domain.features.authorization.AuthRepository
import ru.sobeninalex.domain.features.authorization.usecase.*

internal val AuthorizationFeatureModule = module {
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    factory { AccountApiService() }
}