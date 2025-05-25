package ru.sobeninalex.account.presentation.profile

import ru.sobeninalex.domain.features.account.model.Profile
import ru.sobeninalex.domain.features.post.model.Post

sealed interface ProfileAction {

    data object LoadMorePosts : ProfileAction

    data object Refresh : ProfileAction

    data class OnFollowButtonClick(val profile: Profile) : ProfileAction

    data class OnLikeClick(val post: Post): ProfileAction
}