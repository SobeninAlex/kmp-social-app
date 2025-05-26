package com.example.kmp_social_app.glue.features.home

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.home.di.InternalFeatureHomeModule
import ru.sobeninalex.home.domain.FeatureHomeRepository

val FeatureHomeModule = module {
    singleOf(::FeatureHomeRepositoryImpl).bind<FeatureHomeRepository>()
    includes(InternalFeatureHomeModule)
}