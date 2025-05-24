package ru.sobeninalex.account.presentation.profile

import androidx.compose.runtime.Immutable
import ru.sobeninalex.domain.features.account.model.Profile
import ru.sobeninalex.domain.features.post.model.Post

@Immutable
data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: Profile? = null,
    val posts: List<Post> = emptyList(),
    val endReached: Boolean = true,
    val followingOperation: Boolean = false,
) {

    companion object {
        val Preview = ProfileUiState(
            isLoading = false,
            profile = Profile.Preview,
            posts = Post.PreviewPostList
        )
    }
}
