package ru.sobeninalex.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.sobeninalex.common.compose.CustomRotatingDotsLoader
import ru.sobeninalex.common.compose.CustomTopBar
import ru.sobeninalex.common.compose.PostListItem
import ru.sobeninalex.common.compose.PostListItemShimmer
import ru.sobeninalex.common.compose.PullRefreshLayout
import ru.sobeninalex.common.navigation.LocalNavController
import ru.sobeninalex.common.navigation.MainGraph
import ru.sobeninalex.home.presentation.components.OnBoardingBlock
import ru.sobeninalex.resources.R

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

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Welcome",
                actions = {
                    IconButton(
                        onClick = { /*todo*/ }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.person_circle_icon),
                            contentDescription = null,
                        )
                    }
                }
            )
        },
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
                        isDetailScreen = false,
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
}

@Preview
@Composable
private fun SignUpScreenContentPreview() {
    HomeScreenContent(
        uiState = HomeUiState.Preview,
        action = {}
    )
}