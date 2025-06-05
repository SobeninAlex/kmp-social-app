package ru.sobeninalex.home.presentation.post_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.sobeninalex.common.compose.CustomDialog
import ru.sobeninalex.common.compose.CustomRotatingDotsLoader
import ru.sobeninalex.common.compose.CustomTopBar
import ru.sobeninalex.common.compose.PostListItem
import ru.sobeninalex.common.compose.PostListItemShimmer
import ru.sobeninalex.common.compose.PullRefreshLayout
import ru.sobeninalex.common.compose.SubmitButton
import ru.sobeninalex.common.compose.TitleBottomSheet
import ru.sobeninalex.common.navigation.LocalNavController
import ru.sobeninalex.common.navigation.MainGraph
import ru.sobeninalex.home.presentation.components.OnBoardingBlock
import ru.sobeninalex.resources.Buttons_Medium14
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Title_Bold14
import ru.sobeninalex.resources.White

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        action = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    action: (HomeAction) -> Unit
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

    val visibilityFAB by remember {
        derivedStateOf { !lazyListState.isScrollInProgress }
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Welcome",
                actions = {
                    IconButton(
                        onClick = { showBottomSheet = true }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.person_circle_icon),
                            contentDescription = null,
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = visibilityFAB,
                enter = fadeIn(
                    animationSpec = tween(delayMillis = 1000)
                ) + slideInHorizontally(
                    initialOffsetX = { it / 2 },
                    animationSpec = tween(delayMillis = 1000)
                ),
                exit = fadeOut() + slideOutHorizontally(
                    targetOffsetX = { it / 2 }
                )
            ) {
                SubmitButton(
                    onClick = {
                        navController.navigate(MainGraph.CreatePostRoute)
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                        )

                        Text(
                            modifier = Modifier.padding(end = 4.dp),
                            text = stringResource(R.string.add_new_post),
                            style = Buttons_Medium14
                        )
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.EndOverlay
    ) { scaffoldPadding ->
        PullRefreshLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            isRefreshing = uiState.isRefreshing,
            onRefresh = { action(HomeAction.Refresh) },
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.showUsersRecommendation && uiState.users.isNotEmpty()) {
                    item {
                        OnBoardingBlock(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem(),
                            users = uiState.users,
                            onUserClick = { user ->
                                navController.navigate(MainGraph.ProfileRoute(user.id))
                            },
                            onFollowButtonClick = { followUser ->
                                action(HomeAction.OnFollowButtonClick(followedUser = followUser))
                            },
                            onBoardingFinishClick = {
                                action(HomeAction.OnBoardingFinishClick)
                            }
                        )
                    }
                }

                items(
                    items = uiState.posts,
                    key = { it.postId },
                ) { post ->
                    PostListItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItem(),
                        post = post,
                        onPostClick = {
                            navController.navigate(MainGraph.PostDetailRoute(postId = post.postId))
                        },
                        onProfileClick = {
                            navController.navigate(MainGraph.ProfileRoute(userId = it))
                        },
                        onLikeClick = {
                            action(HomeAction.OnLikeClick(post = post))
                        },
                        onCommentClick = {
                            action(HomeAction.OnCommentClick(post = post))
                        },
                        onDeleteClick = { action(HomeAction.ShowDeletePostDialog(post)) }
                    )
                }

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
            action(HomeAction.LoadMorePosts)
        }
    }

    if (uiState.deletePostDialogState.show) {
        uiState.deletePostDialogState.post?.let { post ->
            CustomDialog(
                title = stringResource(R.string.delete_post_question),
                subtitle = stringResource(R.string.impossible_to_restore),
                onDismissRequest = { action(HomeAction.HideDeletePostDialog) },
                onConfirmClick = {
                    action(HomeAction.HideDeletePostDialog)
                    action(HomeAction.OnDeletePost(post))
                }
            )
        }
    }

    if (showBottomSheet) {
        TitleBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            title = "title sheet",
            subTitle = "subtitle sheet",
            footer = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Button",
                            style = Title_Bold14,
                            color = White
                        )
                    }

                    Button(
                        onClick = {},
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Button",
                            style = Title_Bold14,
                            color = White
                        )
                    }
                }
            }
        ) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {
                items(count = 20) {
                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Button $it",
                            style = Title_Bold14,
                            color = White
                        )
                    }
                }
            }
        }
    }
}