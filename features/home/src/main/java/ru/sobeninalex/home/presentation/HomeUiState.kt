package ru.sobeninalex.home.presentation

import androidx.compose.runtime.Immutable
import ru.sobeninalex.domain.features.follows.model.FollowUser
import ru.sobeninalex.domain.features.post.model.Post

@Immutable
data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val posts: List<Post> = emptyList(),
    val users: List<FollowUser> = emptyList(),
    val showUsersRecommendation: Boolean = true,
    val endReached: Boolean = true,
) {
    companion object {
        val Preview = HomeUiState(
            posts = Post.PreviewPostList,
            users = FollowUser.PreviewFollowUserList,
        )
    }
}