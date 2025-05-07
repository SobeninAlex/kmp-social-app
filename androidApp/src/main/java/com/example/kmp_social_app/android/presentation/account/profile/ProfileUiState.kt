package com.example.kmp_social_app.android.presentation.account.profile

import androidx.compose.runtime.Immutable
import com.example.kmp_social_app.feature.post.domain.model.Post
import com.example.kmp_social_app.feature.profile.domain.model.Profile

@Immutable
data class ProfileUiState(
    val isLoading: Boolean = false,
    val profile: Profile = Profile.Default,
    val postsState: PostsState = PostsState(),
) {

    companion object {
        val Preview = ProfileUiState(
            isLoading = false,
            profile = Profile.Preview,
            postsState = PostsState.Preview
        )
    }
}

@Immutable
data class PostsState(
    val isLoading: Boolean = false,
    val posts: List<Post> = emptyList()
) {

    companion object {
        val Preview = PostsState(
            isLoading = false,
            posts = Post.PreviewPostList
        )
    }
}
