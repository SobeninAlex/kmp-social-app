package ru.sobeninalex.data.remote.features.post

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.domain.features.post.PostRepository

internal val PostFeatureModule = module {
    singleOf(::PostRepositoryImpl).bind<PostRepository>()
    factory { PostApiService() }
}