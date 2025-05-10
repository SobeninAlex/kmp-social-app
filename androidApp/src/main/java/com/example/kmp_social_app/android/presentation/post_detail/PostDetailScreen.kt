package com.example.kmp_social_app.android.presentation.post_detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmp_social_app.android.common.components.CustomTopBar
import com.example.kmp_social_app.android.common.components.ErrorScreen
import com.example.kmp_social_app.android.common.components.LoadingLayout
import com.example.kmp_social_app.android.common.components.PostListItem
import com.example.kmp_social_app.android.common.navigation.LocalNavController
import com.example.kmp_social_app.android.common.theme.KmpSocialAppTheme
import com.example.kmp_social_app.android.presentation.post_detail.components.postDetailCommentsBlock
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PostDetailScreen(
    postId: String
) {
    val viewModel = koinViewModel<PostDetailViewModel>(
        parameters = {
            parametersOf(postId)
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PostDetailScreenContent(
        uiState = uiState,
        action = viewModel::onAction
    )
}

@Composable
private fun PostDetailScreenContent(
    uiState: PostDetailUiState,
    action: (PostDetailAction) -> Unit
) {
    val navController = LocalNavController.current

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "",
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
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.secondary),
            ) {
                item {
                    uiState.post?.let {
                        PostListItem(
                            post = it,
                            onPostClick = {},
                            onProfileClick = { /**todo*/ },
                            onLikeClick = { /**todo*/ },
                            onCommentClick = {},
                            isDetailScreen = true,
                        )
                    }
                }

                postDetailCommentsBlock(
                    isLoading = uiState.commentsState.isLoading,
                    onAddCommentClick = {},
                    comments = uiState.commentsState.comments,
                    onProfileClick = {},
                    onMoreIconClick = {}
                )
            }

            uiState.errorMessage?.let {
                ErrorScreen(
                    errorMessage = it,
                    onClick = { action(PostDetailAction.Retry) }
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostDetailScreenContentPreview() {
    KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PostDetailScreenContent(
                uiState = PostDetailUiState.Preview,
                action = {}
            )
        }
    }
}