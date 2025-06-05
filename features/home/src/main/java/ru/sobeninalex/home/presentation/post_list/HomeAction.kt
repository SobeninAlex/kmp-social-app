package ru.sobeninalex.home.presentation.post_list

import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post

internal sealed interface HomeAction {

    data object Refresh : HomeAction

    data object OnBoardingFinishClick : HomeAction

    data class OnFollowButtonClick(val followedUser: FollowUser) : HomeAction

    data class OnLikeClick(val post: Post) : HomeAction

    data class OnCommentClick(val post: Post) : HomeAction

    data object LoadMorePosts : HomeAction

    data class UpdatePost(val post: Post) : HomeAction

    data class ShowDeletePostDialog(val post: Post) : HomeAction

    data object HideDeletePostDialog : HomeAction

    data class OnDeletePost(val post: Post) : HomeAction
}