package com.example.kmp_social_app.android.presentation.account.follows

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmp_social_app.android.common.components.CustomTopBar
import com.example.kmp_social_app.android.common.components.EmptyScreen
import com.example.kmp_social_app.android.common.components.ErrorScreen
import com.example.kmp_social_app.android.common.components.FollowUserListItem
import com.example.kmp_social_app.android.common.components.LoadingLayout
import com.example.kmp_social_app.android.common.navigation.LocalNavController
import com.example.kmp_social_app.android.common.navigation.MainGraph
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
        event = viewModel::onEvent
    )
}

@Composable
private fun FollowsScreenContent(
    uiState: FollowsUiState,
    event: (FollowsEvent) -> Unit
) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            CustomTopBar(
                title = uiState.followsType.value,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { scaffoldPadding ->
        LoadingLayout(
            isLoading = uiState.isLoading,
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            loadingContent = { /* todo shimmer */ }
        ) {
            if (uiState.followsUsers.isEmpty()) {
                EmptyScreen(title = "Not find Users")
            }

            uiState.errorMessage?.let {
                ErrorScreen(
                    errorMessage = it,
                    onClick = { event(FollowsEvent.Retry) }
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = uiState.followsUsers,
                    key = { it.id }
                ) {
                    FollowUserListItem(
                        followUser = it,
                        onItemClick = { navController.navigate(MainGraph.ProfileRoute(it.id)) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FollowsScreenContentPreview() {
    FollowsScreenContent(
        uiState = FollowsUiState.Preview,
        event = {}
    )
}