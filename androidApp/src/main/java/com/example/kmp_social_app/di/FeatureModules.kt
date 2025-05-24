package com.example.kmp_social_app.di

import ru.sobeninalex.account.di.AccountFeatureModule
import ru.sobeninalex.authorization.di.AuthorizationFeatureModule
import ru.sobeninalex.home.di.HomeFeatureModule
import ru.sobeninalex.post_detail.di.PostDetailFeatureModule

val FeatureModules = listOf(
    AccountFeatureModule,
    AuthorizationFeatureModule,
    HomeFeatureModule,
    PostDetailFeatureModule
)