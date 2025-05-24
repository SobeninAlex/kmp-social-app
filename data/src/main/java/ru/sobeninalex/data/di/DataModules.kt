package ru.sobeninalex.data.di

import ru.sobeninalex.data.remote.features.account.AccountFeatureModule
import ru.sobeninalex.data.remote.features.authorization.AuthorizationFeatureModule
import ru.sobeninalex.data.remote.features.follows.FollowsFeatureModule
import ru.sobeninalex.data.remote.features.post.PostFeatureModule

fun getDataModules() = listOf(
    AccountFeatureModule,
    AuthorizationFeatureModule,
    FollowsFeatureModule,
    PostFeatureModule,
)