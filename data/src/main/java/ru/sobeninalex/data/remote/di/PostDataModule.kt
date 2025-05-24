package ru.sobeninalex.data.remote.di

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sobeninalex.data.remote.features.post.PostApiService
import ru.sobeninalex.data.remote.features.post.PostRepositoryImpl
import ru.sobeninalex.domain.features.post.PostRepository

val PostDataModule = module {
    singleOf(::PostRepositoryImpl).bind<PostRepository>()
    factory { PostApiService() }
}