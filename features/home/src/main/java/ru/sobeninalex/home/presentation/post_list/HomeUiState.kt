package ru.sobeninalex.home.presentation.post_list

import androidx.compose.runtime.Immutable
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post

@Immutable
internal data class HomeUiState(
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