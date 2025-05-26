package ru.sobeninalex.account.presentation.follows

import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.follow.FollowsType

internal data class FollowsUiState(
    val isLoading: Boolean = false,
    val followUsers: List<FollowUser> = emptyList(),
    val followsType: FollowsType = FollowsType.FOLLOWERS,
    val errorMessage: String? = null,
    val endReached: Boolean = true
) {

    companion object {
        val Preview = FollowsUiState(
            isLoading = false,
            followUsers = FollowUser.PreviewFollowUserList,
            errorMessage = null
        )
    }
}