package ru.sobeninalex.account.presentation.edit

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ru.sobeninalex.common.compose.CircleImage
import ru.sobeninalex.common.compose.CustomTetField
import ru.sobeninalex.common.compose.CustomTopBar
import ru.sobeninalex.common.compose.LoadingDialog
import ru.sobeninalex.common.compose.SubmitButton
import ru.sobeninalex.common.navigation.LocalNavController
import ru.sobeninalex.common.navigation.args.EditProfileArgs
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Title_Bold16
import ru.sobeninalex.resources.roundedCornerShape4

@Composable
fun EditProfileScreen(
    args: EditProfileArgs
) {
    val viewModel = koinViewModel<EditProfileViewModel>(
        parameters = {
            parametersOf(args)
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    EditProfileScreenContent(
        uiState = uiState,
        action = viewModel::onAction
    )
}

@Composable
private fun EditProfileScreenContent(
    uiState: EditProfileUiState,
    action: (EditProfileAction) -> Unit
) {
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()
    var selectedImage by remember { mutableStateOf(Uri.EMPTY) }
    val pickImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let {
                selectedImage = uri
            }
        },
    )

    LaunchedEffect(uiState.updateSucceed) {
        if (uiState.updateSucceed) {
            navController.popBackStack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.edit_profile_label),
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box {
                CircleImage(
                    modifier = Modifier.size(240.dp),
                    imageUrl = uiState.profile.avatar,
                    uri = selectedImage
                )

                IconButton(
                    onClick = {
                        pickImage.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .shadow(
                            elevation = 2.dp,
                            shape = roundedCornerShape4
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = roundedCornerShape4
                        )
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            CustomTetField(
                modifier = Modifier.fillMaxWidth(),
                isSingleLine = true,
                value = uiState.profile.name,
                onValueChange = { action(EditProfileAction.EditName(it)) },
                placeholder = R.string.username_hint
            )

            CustomTetField(
                modifier = Modifier.fillMaxWidth(),
                isSingleLine = false,
                value = uiState.profile.bio,
                onValueChange = { action(EditProfileAction.EditBio(it)) },
                placeholder = R.string.user_bio_hint
            )

            SubmitButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    action(EditProfileAction.OnUpdateProfileClick(imageUri = selectedImage))
                },
            ) {
                Text(
                    text = stringResource(R.string.upload_changes_text),
                    style = Title_Bold16
                )
            }
        }
    }

    if (uiState.isLoading) {
        LoadingDialog()
    }
}

@Preview
@Composable
private fun EditProfileScreenContentPreview() {
    EditProfileScreenContent(
        uiState = EditProfileUiState.Preview,
        action = {}
    )
}