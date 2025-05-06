package com.example.kmp_social_app.android.presentation.account.follows

import com.example.kmp_social_app.feature.follows.domain.model.FollowUser

data class FollowsUiState(
    val isLoading: Boolean = false,
    val followsUsers: List<FollowUser> = emptyList(),
    val followsType: FollowsType = FollowsType.FOLLOWERS,
    val errorMessage: String? = null
) {

    companion object {
        val Preview = FollowsUiState(
            isLoading = false,
            followsUsers = FollowUser.PreviewFollowUserList,
            errorMessage = null
        )
    }
}