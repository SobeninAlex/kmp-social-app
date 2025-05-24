package ru.sobeninalex.data.remote.features.follows

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.domain.features.follows.FollowsRepository

internal val FollowsFeatureModule = module {
    singleOf(::FollowsRepositoryImpl).bind<FollowsRepository>()
    factory { FollowsApiService() }
}