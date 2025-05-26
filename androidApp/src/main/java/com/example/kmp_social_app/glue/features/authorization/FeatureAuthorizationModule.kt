package com.example.kmp_social_app.glue.features.authorization

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.authorization.di.InternalFeatureAuthorizationModule
import ru.sobeninalex.authorization.domain.FeatureAuthorizationRepository

val FeatureAuthorizationModule = module {
    singleOf(::FeatureAuthorizationRepositoryImpl).bind<FeatureAuthorizationRepository>()
    includes(InternalFeatureAuthorizationModule)
}