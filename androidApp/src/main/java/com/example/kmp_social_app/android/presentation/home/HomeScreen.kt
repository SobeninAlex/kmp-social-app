package com.example.kmp_social_app.android.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.CustomTopBar
import com.example.kmp_social_app.android.common.components.PostListItem
import com.example.kmp_social_app.android.common.components.PullRefreshLayout
import com.example.kmp_social_app.android.common.navigation.LocalNavController
import com.example.kmp_social_app.android.common.navigation.MainGraph
import com.example.kmp_social_app.android.presentation.home.components.OnBoardingBlock
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreenContent(
        uiState = uiState,
        event = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    uiState: HomeUiState,
    event: (HomeEvent) -> Unit
) {
    val navController = LocalNavController.current

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
        val pullToRefreshState = rememberPullToRefreshState()

        PullRefreshLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            isRefreshing = uiState.isRefreshing,
            onRefresh = { event(HomeEvent.Refresh) },
            pullToRefreshState = pullToRefreshState,
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (uiState.onBoardingState.shouldShowOnBoarding) {
                    item {
                        if (uiState.onBoardingState.users.isNotEmpty()) {
                            OnBoardingBlock(
                                modifier = Modifier.fillMaxWidth(),
                                users = uiState.onBoardingState.users,
                                onUserClick = {},
                                onFollowButtonClick = { follow, FollowUser -> },
                                onBoardingFinishClick = {}
                            )
                        }
                    }
                }

                items(
                    items = uiState.posts,
                    key = { it.postId },
                ) { post ->
                    PostListItem(
                        modifier = Modifier.fillMaxWidth(),
                        post = post,
                        onPostClick = {
                            navController.navigate(MainGraph.PostDetailRoute(postId = post.postId))
                        },
                        onProfileClick = {
                            navController.navigate(MainGraph.ProfileRoute(userId = it))
                        },
                        onLikeClick = {},
                        onCommentClick = {},
                        isDetailScreen = false
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignUpScreenContentPreview() {
    HomeScreenContent(
        uiState = HomeUiState.Preview,
        event = {}
    )
}