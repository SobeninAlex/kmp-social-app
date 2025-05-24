package ru.sobeninalex.data.remote.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.data.remote.features.follows.FollowsApiService
import ru.sobeninalex.data.remote.features.follows.FollowsRepositoryImpl
import ru.sobeninalex.domain.features.follows.FollowsRepository

val FollowsDataModule = module {
    singleOf(::FollowsRepositoryImpl).bind<FollowsRepository>()
    factory { FollowsApiService() }
}