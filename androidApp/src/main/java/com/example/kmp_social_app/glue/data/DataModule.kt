package com.example.kmp_social_app.glue.data

import com.example.kmp_social_app.glue.data.datasource.AccountApiDataSourceImpl
import com.example.kmp_social_app.glue.data.datasource.AuthApiDataSourceImpl
import com.example.kmp_social_app.glue.data.datasource.FollowsApiDataSourceImpl
import com.example.kmp_social_app.glue.data.datasource.PostApiDataSourceImpl
import org.koin.dsl.module
import ru.sobeninalex.data.remote.di.ApiServiceModule
import ru.sobeninalex.data.remote.services.account.AccountApiDataSource
import ru.sobeninalex.data.remote.services.authorization.AuthApiDataSource
import ru.sobeninalex.data.remote.services.follows.FollowsApiDataSource
import ru.sobeninalex.data.remote.services.post.PostApiDataSource

val DataModule = module {
    includes(ApiServiceModule)

    factory<AccountApiDataSource> {
        AccountApiDataSourceImpl(
            userPreferences = get(),
            accountApiService = get()
        )
    }

    factory<AuthApiDataSource> {
        AuthApiDataSourceImpl(
            userPreferences = get(),
            authApiService = get()
        )
    }

    factory<FollowsApiDataSource> {
        FollowsApiDataSourceImpl(
            userPreferences = get(),
            followsApiService = get()
        )
    }

    factory<PostApiDataSource> {
        PostApiDataSourceImpl(
            userPreferences = get(),
            postApiService = get()
        )
    }
}