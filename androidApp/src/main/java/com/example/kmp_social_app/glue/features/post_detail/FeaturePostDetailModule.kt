package com.example.kmp_social_app.glue.features.post_detail

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.post_detail.di.InternalFeaturePostDetailModule
import ru.sobeninalex.post_detail.domain.FeaturePostDetailRepository

val FeaturePostDetailModule = module {
    singleOf(::FeaturePostDetailRepositoryImpl).bind<FeaturePostDetailRepository>()
    includes(InternalFeaturePostDetailModule)
}