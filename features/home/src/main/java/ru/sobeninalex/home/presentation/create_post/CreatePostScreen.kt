package ru.sobeninalex.home.presentation.create_post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.sobeninalex.common.compose.CustomTetField
import ru.sobeninalex.common.compose.CustomTopBar
import ru.sobeninalex.common.compose.ImageCard
import ru.sobeninalex.common.compose.LoadingDialog
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
        onAction = viewModel::onAction
    )
}

@Composable
private fun CreatePostScreenContent(
    uiState: CreatePostUiState,
    onAction: (CreatePostAction) -> Unit
) {
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()
    val pickImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                onAction(CreatePostAction.OnChangeImageUri(it))
            }
        },
    )

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
                .padding(16.dp)
                .padding(scaffoldPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                ) {
                    if (uiState.imageUri == Uri.EMPTY) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(roundedCornerShape20)
                                .clickable {
                                    pickImage.launch(
                                        PickVisualMediaRequest(
                                            ActivityResultContracts.PickVisualMedia.ImageOnly
                                        )
                                    )
                                }
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
                    } else {
                        ImageCard(
                            model = uiState.imageUri,
                            modifier = Modifier
                                .clip(roundedCornerShape20)
                                .fillMaxWidth()
                        )
                    }
                }

                CustomTetField(
                    modifier = Modifier.fillMaxWidth(),
                    isSingleLine = false,
                    value = uiState.caption,
                    onValueChange = { onAction(CreatePostAction.OnChangeCaption(it)) },
                    placeholder = R.string.post_caption_hint
                )
            }

            SubmitButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomCenter),
                enabled = uiState.createPostButtonEnabled,
                onClick = {
                    onAction(CreatePostAction.OnCreatePostClick)
                },
            ) {
                Text(
                    text = stringResource(R.string.create_post_button_label),
                    style = Buttons_Medium14
                )
            }
        }

    }

    if (uiState.isLoading) {
        LoadingDialog()
    }
}