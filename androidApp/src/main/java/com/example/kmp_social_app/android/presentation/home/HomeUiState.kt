package com.example.kmp_social_app.android.presentation.home

import androidx.compose.runtime.Immutable
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import com.example.kmp_social_app.feature.post.domain.model.Post

@Immutable
data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val posts: List<Post> = emptyList(),
    val users: List<FollowUser> = emptyList(),
    val showUsersRecommendation: Boolean = true,
) {
    companion object {
        val Preview = HomeUiState(
            posts = Post.PreviewPostList,
            users = FollowUser.PreviewFollowUserList,
        )
    }
}