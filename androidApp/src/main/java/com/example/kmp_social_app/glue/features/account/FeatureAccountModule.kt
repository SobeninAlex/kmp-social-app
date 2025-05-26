package com.example.kmp_social_app.glue.features.account

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.account.di.InternalFeatureAccountModule
import ru.sobeninalex.account.domain.FeatureAccountRepository

val FeatureAccountModule = module {
    singleOf(::FeatureAccountRepositoryImpl).bind<FeatureAccountRepository>()
    includes(InternalFeatureAccountModule)
}