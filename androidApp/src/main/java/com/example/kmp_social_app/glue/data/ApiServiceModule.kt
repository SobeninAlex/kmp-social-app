package com.example.kmp_social_app.glue.data

import org.koin.dsl.module
import ru.sobeninalex.data.remote.services.account.AccountApiService
import ru.sobeninalex.data.remote.services.authorization.AuthApiService
import ru.sobeninalex.data.remote.services.follows.FollowsApiService
import ru.sobeninalex.data.remote.services.post.PostApiService

val ApiServiceModule = module {
    factory<AccountApiService> { AccountApiService() }
    factory<AuthApiService> { AuthApiService() }
    factory<FollowsApiService> { FollowsApiService() }
    factory<PostApiService> { PostApiService() }
}