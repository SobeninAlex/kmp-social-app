package ru.sobeninalex.account.presentation.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.sobeninalex.account.presentation.profile.components.profileHeaderBlock
import ru.sobeninalex.account.presentation.profile.components.profilePostsBlock
import ru.sobeninalex.common.compose.CustomRotatingDotsLoader
import ru.sobeninalex.common.compose.CustomTopBar
import ru.sobeninalex.common.compose.LoadingDialog
import ru.sobeninalex.common.compose.PostListItemShimmer
import ru.sobeninalex.common.models.follow.FollowsType
import ru.sobeninalex.common.navigation.LocalNavController
import ru.sobeninalex.common.navigation.MainGraph
import ru.sobeninalex.common.navigation.args.EditProfileArgs
import ru.sobeninalex.common.navigation.args.FollowsArgs
import ru.sobeninalex.resources.R

@Composable
fun ProfileScreen(
    userId: String
) {
    val viewModel = koinViewModel<ProfileViewModel>(
        parameters = {
            parametersOf(userId)
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(ProfileUiState())

    ProfileScreenContent(
        uiState = uiState,
        action = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreenContent(
    uiState: ProfileUiState,
    action: (ProfileAction) -> Unit
) {
    val navController = LocalNavController.current

    val lazyListState = rememberLazyListState()

    val shouldLoadMorePosts by remember {
        derivedStateOf {
            if (lazyListState.layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.last()
                (lastVisibleItem.index == lazyListState.layoutInfo.totalItemsCount - 1)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.profile_destination_title),
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                uiState.profile?.let { profile ->
                    profileHeaderBlock(
                        profile = profile,
                        followingOperation = uiState.followingOperation,
                        onFollowClick = {
                            action(ProfileAction.OnFollowButtonClick(profile = profile))
                        },
                        onFollowersClick = {
                            navController.navigate(
                                MainGraph.FollowsRoute(
                                    args = FollowsArgs(
                                        userId = profile.id,
                                        followsType = FollowsType.FOLLOWERS
                                    )
                                )
                            )
                        },
                        onFollowingClick = {
                            navController.navigate(
                                MainGraph.FollowsRoute(
                                    args = FollowsArgs(
                                        userId = profile.id,
                                        followsType = FollowsType.FOLLOWING
                                    )
                                )
                            )
                        },
                        onEditProfileClick = {
                            navController.navigate(
                                MainGraph.EditProfileRoute(
                                    EditProfileArgs(profile = profile)
                                )
                            )
                        },
                    )
                }

                profilePostsBlock(
                    isLoading = uiState.isLoading,
                    posts = uiState.posts,
                    onPostClick = {
                        navController.navigate(MainGraph.PostDetailRoute(postId = it.postId))
                    },
                    onLikeClick = {
                        action(ProfileAction.OnLikeClick(post = it))
                    },
                    onCommentClick = {},
                    isOwnProfile = uiState.profile?.isOwnProfile ?: false
                )

                if (uiState.isLoading && !uiState.endReached) {
                    item {
                        PostListItemShimmer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem()
                        )
                    }
                }
            }

            CustomRotatingDotsLoader(
                isLoading = uiState.isLoading,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    LaunchedEffect(shouldLoadMorePosts) {
        if (shouldLoadMorePosts && !uiState.endReached) {
            action(ProfileAction.LoadMorePosts)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun ProfileScreenContentPreview() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileScreenContent(
                uiState = ProfileUiState.Preview,
                action = {}
            )
        }
    }
}