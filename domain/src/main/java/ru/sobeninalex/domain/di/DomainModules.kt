package ru.sobeninalex.domain.di

import ru.sobeninalex.domain.features.account.AccountFeatureModule
import ru.sobeninalex.domain.features.authorization.AuthorizationFeatureModule
import ru.sobeninalex.domain.features.follows.FollowsFeatureModule
import ru.sobeninalex.domain.features.post.PostFeatureModule

fun getDomainModules() = listOf(
    AccountFeatureModule,
    AuthorizationFeatureModule,
    FollowsFeatureModule,
    PostFeatureModule,
)