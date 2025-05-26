package ru.sobeninalex.account.presentation.profile

import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.profile.Profile

internal sealed interface ProfileAction {

    data object LoadMorePosts : ProfileAction

    data object Refresh : ProfileAction

    data class OnFollowButtonClick(val profile: Profile) : ProfileAction

    data class OnLikeClick(val post: Post): ProfileAction
}