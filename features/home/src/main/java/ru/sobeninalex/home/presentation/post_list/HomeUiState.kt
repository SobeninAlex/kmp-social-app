package ru.sobeninalex.home.presentation.post_list

import androidx.compose.runtime.Immutable
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.common.models.post.Post

@Immutable
internal data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val throwable: Throwable? = null,
    val posts: List<Post> = emptyList(),
    val users: List<FollowUser> = emptyList(),
    val showUsersRecommendation: Boolean = true,
    val endReached: Boolean = true,
    val deletePostDialogState: DeletePostDialogState = DeletePostDialogState(),
) {
    companion object {
        val Preview = HomeUiState(
            posts = Post.PreviewPostList,
            users = FollowUser.PreviewFollowUserList,
        )
    }
}

@Immutable
internal data class DeletePostDialogState(
    val show: Boolean = false,
    val post: Post? = null
)