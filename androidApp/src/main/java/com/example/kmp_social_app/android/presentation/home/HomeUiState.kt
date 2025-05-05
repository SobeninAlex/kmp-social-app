package com.example.kmp_social_app.android.presentation.home

import androidx.compose.runtime.Immutable
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser
import com.example.kmp_social_app.feature.post.domain.model.Post

@Immutable
data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val posts: List<Post> = emptyList(),
    val onBoardingState: OnBoardingState = OnBoardingState()
) {
    companion object {
        val Preview = HomeUiState(
            isLoading = false,
            posts = Post.FAKE_LIST,
            onBoardingState = OnBoardingState.Preview
        )
    }
}

@Immutable
data class OnBoardingState(
    val isLoading: Boolean = false,
    val users: List<FollowUser> = emptyList(),
    val shouldShowOnBoarding: Boolean = false,
) {
    companion object {
        val Preview = OnBoardingState(
            isLoading = false,
            users = FollowUser.FAKE_LIST,
            shouldShowOnBoarding = true
        )
    }
}