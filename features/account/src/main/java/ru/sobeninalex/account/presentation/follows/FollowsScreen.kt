package ru.sobeninalex.account.presentation.follows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.sobeninalex.common.compose.CustomRotatingDotsLoader
import ru.sobeninalex.common.compose.CustomTopBar
import ru.sobeninalex.common.compose.EmptyScreen
import ru.sobeninalex.common.compose.ErrorScreen
import ru.sobeninalex.common.compose.FollowUserListItemShimmer
import ru.sobeninalex.common.navigation.LocalNavController
import ru.sobeninalex.common.navigation.MainGraph
import ru.sobeninalex.common.navigation.args.FollowsArgs
import ru.sobeninalex.common.compose.FollowUserListItem as FollowUserListItem1

@Composable
fun FollowsScreen(
    args: FollowsArgs
) {
    val viewModel = koinViewModel<FollowsViewModel>(
        parameters = {
            parametersOf(args)
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FollowsScreenContent(
        uiState = uiState,
        action = viewModel::onAction
    )
}

@Composable
private fun FollowsScreenContent(
    uiState: FollowsUiState,
    action: (FollowsAction) -> Unit
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
                title = uiState.followsType.value,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {
            EmptyScreen(
                modifier = Modifier.fillMaxSize(),
                visibility = !uiState.isLoading && uiState.followUsers.isEmpty(),
                title = "Not find Users"
            )

            ErrorScreen(
                modifier = Modifier.fillMaxSize(),
                visibility = uiState.errorMessage != null,
                errorMessage = uiState.errorMessage,
                onClick = { action(FollowsAction.Retry) }
            )

            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = uiState.followUsers,
                    key = { it.id }
                ) { followUser ->
                    FollowUserListItem1(
                        modifier = Modifier.animateItem(),
                        followUser = followUser,
                        onItemClick = {
                            navController.navigate(MainGraph.ProfileRoute(userId = followUser.id))
                        }
                    )
                }

                if (uiState.isLoading && !uiState.endReached) {
                    item {
                        FollowUserListItemShimmer(
                            modifier = Modifier.animateItem()
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
            action(FollowsAction.LoadMoreData)
        }
    }
}

@Preview
@Composable
private fun FollowsScreenContentPreview() {
    FollowsScreenContent(
        uiState = FollowsUiState.Preview,
        action = {}
    )
}