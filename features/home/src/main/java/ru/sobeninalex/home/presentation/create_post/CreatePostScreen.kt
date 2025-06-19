package ru.sobeninalex.home.presentation.create_post

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.sobeninalex.common.compose.AttachmentsBottomSheet
import ru.sobeninalex.common.compose.CustomTetField
import ru.sobeninalex.common.compose.CustomTopBar
import ru.sobeninalex.common.compose.ImageCard
import ru.sobeninalex.common.compose.LoadingDialog
import ru.sobeninalex.common.compose.FullscreenMediaPager
import ru.sobeninalex.common.compose.SubmitButton
import ru.sobeninalex.common.navigation.LocalNavController
import ru.sobeninalex.resources.Buttons_Medium14
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.roundedCornerShape20

@Composable
fun CreatePostScreen() {
    val viewModel = koinViewModel<CreatePostViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CreatePostScreenContent(
        uiState = uiState,
        action = viewModel::onAction
    )
}

@Composable
private fun CreatePostScreenContent(
    uiState: CreatePostUiState,
    action: (CreatePostAction) -> Unit
) {
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()

    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.uploadPostSuccess) {
        if (uiState.uploadPostSuccess) {
            navController.popBackStack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.create_post_destination_title),
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
                .padding(scaffoldPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(space = 12.dp, alignment = Alignment.CenterHorizontally),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        items = uiState.attachmentsUri
                    ) { uri ->
                        ImageCard(
                            model = uri,
                            onClick = { action(CreatePostAction.ShowMediaPager(uri = uri)) },
                            modifier = Modifier
                                .clip(roundedCornerShape20)
                                .fillParentMaxWidth(0.9f)
                                .aspectRatio(1f)
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .clip(roundedCornerShape20)
                                .fillParentMaxWidth(0.9f)
                                .aspectRatio(1f)
                                .clickable { showBottomSheet = true }
                        ) {
                            val mock = if (isSystemInDarkTheme()) {
                                painterResource(R.drawable.img_mock_picture_dark)
                            } else {
                                painterResource(R.drawable.img_mock_picture_light)
                            }
                            Image(
                                painter = mock,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(60.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                CustomTetField(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    isSingleLine = false,
                    value = uiState.caption,
                    onValueChange = { action(CreatePostAction.OnChangeCaption(it)) },
                    placeholder = R.string.post_caption_hint
                )
            }

            SubmitButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter),
                enabled = uiState.createPostButtonEnabled,
                onClick = {
                    action(CreatePostAction.OnCreatePostClick)
                },
            ) {
                Text(
                    text = stringResource(R.string.create_post_button_label),
                    style = Buttons_Medium14
                )
            }
        }
    }

    if (uiState.mediaPagerState.show) {
        FullscreenMediaPager(
            mediaFiles = uiState.attachmentsUri,
            startMediaFile = uiState.mediaPagerState.uri,
            onDismissRequest = { action(CreatePostAction.HideMediaPager) }
        )
    }

    if (showBottomSheet) {
        AttachmentsBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            onPick = { action(CreatePostAction.OnPickAttachments(it)) }
        )
    }

    if (uiState.isLoading) {
        LoadingDialog()
    }
}