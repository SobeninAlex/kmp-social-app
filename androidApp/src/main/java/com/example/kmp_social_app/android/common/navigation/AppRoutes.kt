package com.example.kmp_social_app.android.common.navigation

import com.example.kmp_social_app.android.presentation.account.edit.EditProfileArgs
import com.example.kmp_social_app.android.presentation.account.follows.FollowsArgs
import kotlinx.serialization.Serializable

@Serializable
data object AuthGraph {

    @Serializable
    data object SignUpRoute

    @Serializable
    data object LoginRoute
}

@Serializable
data object MainGraph {

    @Serializable
    data object HomeRoute

    @Serializable
    data class PostDetailRoute(val postId: String)

    @Serializable
    data class ProfileRoute(val userId: String)

    @Serializable
    data class EditProfileRoute(val args: EditProfileArgs)

    @Serializable
    data class FollowsRoute(val args: FollowsArgs)
}

