package com.example.kmp_social_app.android.common.navigation

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
}

