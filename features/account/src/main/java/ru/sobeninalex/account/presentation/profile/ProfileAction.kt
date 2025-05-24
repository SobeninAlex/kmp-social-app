package ru.sobeninalex.account.presentation.profile

import com.example.kmp_social_app.feature.account.domain.model.Profile
import com.example.kmp_social_app.feature.post.domain.model.Post

sealed interface ProfileAction {

    data object LoadMorePosts : ProfileAction

    data class OnFollowButtonClick(val profile: Profile) : ProfileAction

    data class OnLikeClick(val post: Post): ProfileAction
}