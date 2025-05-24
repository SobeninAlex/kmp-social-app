package ru.sobeninalex.home.presentation

import ru.sobeninalex.domain.features.follows.model.FollowUser
import ru.sobeninalex.domain.features.post.model.Post

sealed interface HomeAction {

    data object Refresh : HomeAction

    data object OnBoardingFinishClick : HomeAction

    data class OnFollowButtonClick(val followedUser: FollowUser) : HomeAction

    data class OnLikeClick(val post: Post) : HomeAction

    data class OnCommentClick(val post: Post) : HomeAction

    data object LoadMorePosts : HomeAction

    data class UpdatePost(val post: Post) : HomeAction
}