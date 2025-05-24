package ru.sobeninalex.account.presentation.profile

import androidx.compose.runtime.Immutable
import com.example.kmp_social_app.feature.post.domain.model.Post
import com.example.kmp_social_app.feature.account.domain.model.Profile

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
