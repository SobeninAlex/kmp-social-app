package ru.sobeninalex.account.presentation.follows

internal sealed interface FollowsAction {

    data object LoadMoreData : FollowsAction

    data object Retry : FollowsAction
}