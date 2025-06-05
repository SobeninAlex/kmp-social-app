package ru.sobeninalex.account.presentation.profile

import androidx.compose.runtime.Immutable
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.common.models.profile.Profile

@Immutable
internal data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val posts: List<Post> = emptyList(),
    val endReached: Boolean = true,
    val followingOperation: Boolean = false,
    val deletePostDialogState: DeletePostDialogState = DeletePostDialogState(),
) {

    companion object {
        val Preview = ProfileUiState(
            isLoading = false,
            profile = Profile.Preview,
            posts = Post.PreviewPostList
        )
    }
}

@Immutable
internal data class DeletePostDialogState(
    val show: Boolean = false,
    val post: Post? = null
)
