package ru.sobeninalex.account.presentation.follows

import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import com.example.kmp_social_app.feature.follows.domain.model.FollowsType

data class FollowsUiState(
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