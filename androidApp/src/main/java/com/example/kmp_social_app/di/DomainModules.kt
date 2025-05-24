package com.example.kmp_social_app.di

import ru.sobeninalex.domain.di.AccountDomainModule
import ru.sobeninalex.domain.di.AuthorizationDomainModule
import ru.sobeninalex.domain.di.FollowsDomainModule
import ru.sobeninalex.domain.di.PostDomainModule

val DomainModules = listOf(
    AccountDomainModule,
    AuthorizationDomainModule,
    FollowsDomainModule,
    PostDomainModule,
)