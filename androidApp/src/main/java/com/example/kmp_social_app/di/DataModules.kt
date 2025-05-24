package com.example.kmp_social_app.di

import ru.sobeninalex.data.remote.di.AccountDataModule
import ru.sobeninalex.data.remote.di.AuthorizationDataModule
import ru.sobeninalex.data.remote.di.FollowsDataModule
import ru.sobeninalex.data.remote.di.PostDataModule

val DataModules = listOf(
    AccountDataModule,
    AuthorizationDataModule,
    FollowsDataModule,
    PostDataModule,
)