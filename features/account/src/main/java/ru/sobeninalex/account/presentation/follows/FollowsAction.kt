package ru.sobeninalex.account.presentation.follows

sealed interface FollowsAction {

    data object LoadMoreData : FollowsAction

    data object Retry : FollowsAction
}