package com.example.kmp_social_app.presentation.post_detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.sobeninalex.utils.compose.CommentListItemShimmer
import ru.sobeninalex.utils.compose.CustomRotatingDotsLoader
import ru.sobeninalex.utils.compose.CustomTopBar
import ru.sobeninalex.utils.compose.InputBottomSheet
import ru.sobeninalex.utils.compose.PostListItem
import com.example.kmp_social_app.navigation.LocalNavController
import ru.sobeninalex.resources.KmpSocialAppTheme
import com.example.kmp_social_app.presentation.post_detail.components.postDetailCommentsBlock
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PostDetailScreen(
    postId: String,
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
            ru.sobeninalex.utils.compose.CustomTopBar(
                title = "",
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
                modifier = Modifier.fillMaxSize()
            ) {
                uiState.post?.let {
                    item {
                        /**todo*/
                        ru.sobeninalex.utils.compose.PostListItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem(),
                            post = uiState.post,
                            onPostClick = {},
                            onProfileClick = { /**todo*/ },
                            onLikeClick = { action(PostDetailAction.OnLikeClick) },
                            onCommentClick = {},
                            isDetailScreen = true,
                        )
                    }

                    postDetailCommentsBlock(
                        isLoading = uiState.isLoading,
                        onAddCommentClick = { action(PostDetailAction.OnAddCommentClick) },
                        comments = uiState.comments,
                        onProfileClick = {},
                        onDeleteClick = {
                            action(PostDetailAction.DeleteComment(it))
                        }
                    )
                }

                if (uiState.isLoading && !uiState.endReached) {
                    item {
                        ru.sobeninalex.utils.compose.CommentListItemShimmer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItem()
                        )
                    }
                }
            }

            ru.sobeninalex.utils.compose.CustomRotatingDotsLoader(
                isLoading = uiState.isLoading,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    LaunchedEffect(shouldLoadMorePosts) {
        if (shouldLoadMorePosts && !uiState.endReached) {
            action(PostDetailAction.LoadMoreComments)
        }
    }

    if (uiState.bottomSheetState.isOpen) {
        when (uiState.bottomSheetState.type) {
            BottomSheetState.Type.AddComment -> {
                ru.sobeninalex.utils.compose.InputBottomSheet(
                    text = "",
                    isLoading = uiState.isAddingNewComment,
                    onSendClick = { action(PostDetailAction.OnSendCommentClick(it)) },
                    onDismissRequest = { action(PostDetailAction.CloseBottomSheet) }
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostDetailScreenContentPreview() {
    ru.sobeninalex.resources.KmpSocialAppTheme {
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