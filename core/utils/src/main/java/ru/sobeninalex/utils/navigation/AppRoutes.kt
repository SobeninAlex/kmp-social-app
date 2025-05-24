package ru.sobeninalex.utils.navigation

import kotlinx.serialization.Serializable
import ru.sobeninalex.utils.navigation.args.EditProfileArgs
import ru.sobeninalex.utils.navigation.args.FollowsArgs

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

