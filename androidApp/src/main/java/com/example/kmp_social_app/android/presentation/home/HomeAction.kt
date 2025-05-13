package com.example.kmp_social_app.android.presentation.home

import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import com.example.kmp_social_app.feature.post.domain.model.Post

sealed interface HomeAction {

    data object Refresh : HomeAction

    data object OnBoardingFinishClick : HomeAction

    data class OnFollowButtonClick(val followedUser: FollowUser) : HomeAction

    data class OnLikeClick(val post: Post) : HomeAction

    data class OnCommentClick(val post: Post) : HomeAction

    data object LoadMorePosts : HomeAction

    data class UpdatePost(val post: Post) : HomeAction
}