package com.example.kmp_social_app.android.presentation.account.profile

import android.content.res.Configuration
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.CustomRotatingDotsLoader
import com.example.kmp_social_app.android.common.components.CustomTopBar
import com.example.kmp_social_app.android.common.components.LoadingLayout
import com.example.kmp_social_app.android.common.components.PostListItem
import com.example.kmp_social_app.android.common.components.SubmitButton
import com.example.kmp_social_app.android.common.navigation.LocalNavController
import com.example.kmp_social_app.android.common.navigation.MainGraph
import com.example.kmp_social_app.android.common.theme.KmpSocialAppTheme
import com.example.kmp_social_app.android.presentation.account.edit.EditProfileArgs
import com.example.kmp_social_app.android.presentation.account.edit.toProfileArgs
import com.example.kmp_social_app.android.presentation.account.follows.FollowsArgs
import com.example.kmp_social_app.android.presentation.account.follows.FollowsType
import com.example.kmp_social_app.android.presentation.account.profile.components.ProfileHeaderBlock
import com.example.kmp_social_app.android.presentation.account.profile.components.profileHeaderBlock
import com.example.kmp_social_app.android.presentation.account.profile.components.profilePostsBlock
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProfileScreen(
    userId: String
) {
    val viewModel = koinViewModel<ProfileViewModel>(
        parameters = {
            parametersOf(userId)
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileScreenContent(
        uiState = uiState,
        event = viewModel::onEvent
    )
}

@Composable
private fun ProfileScreenContent(
    uiState: ProfileUiState,
    event: (ProfileEvent) -> Unit
) {
    val navController = LocalNavController.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.profile_destination_title),
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { scaffoldPadding ->
        LoadingLayout(
            isLoading = uiState.isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                profileHeaderBlock(
                    profile = uiState.profile,
                    onFollowClick = {},
                    onFollowersClick = {
                        navController.navigate(
                            MainGraph.FollowsRoute(
                                args = FollowsArgs(
                                    userId = uiState.profile.id,
                                    followsType = FollowsType.FOLLOWERS
                                )
                            )
                        )
                    },
                    onFollowingClick = {
                        navController.navigate(
                            MainGraph.FollowsRoute(
                                args = FollowsArgs(
                                    userId = uiState.profile.id,
                                    followsType = FollowsType.FOLLOWING
                                )
                            )
                        )
                    },
                    onEditProfileClick = {
                        navController.navigate(
                            MainGraph.EditProfileRoute(
                                EditProfileArgs(profile = uiState.profile.toProfileArgs())
                            )
                        )
                    },
                )

                profilePostsBlock(
                    isLoading = uiState.postsState.isLoading,
                    posts = uiState.postsState.posts,
                    onPostClick = {
                        navController.navigate(MainGraph.PostDetailRoute(it.postId))
                    },
                    onLikeClick = {},
                    onCommentClick = {},
                    isOwnProfile = uiState.profile.isOwnProfile
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun ProfileScreenContentPreview() {
    KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileScreenContent(
                uiState = ProfileUiState.Preview,
                event = {}
            )
        }
    }
}