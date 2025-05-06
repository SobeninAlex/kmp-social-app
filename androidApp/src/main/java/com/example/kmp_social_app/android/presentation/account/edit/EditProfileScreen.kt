package com.example.kmp_social_app.android.presentation.account.edit

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.CircleImage
import com.example.kmp_social_app.android.common.components.CustomTetField
import com.example.kmp_social_app.android.common.components.CustomTopBar
import com.example.kmp_social_app.android.common.components.LoadingDialog
import com.example.kmp_social_app.android.common.components.SubmitButton
import com.example.kmp_social_app.android.common.navigation.LocalNavController
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
        event = viewModel::onEvent
    )
}

@Composable
private fun EditProfileScreenContent(
    uiState: EditProfileUiState,
    event: (EditProfileEvent) -> Unit
) {
    val navController = LocalNavController.current
    val scrollState = rememberScrollState()

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
                    imageUrl = uiState.profile.avatar
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .shadow(
                            elevation = 2.dp,
                            shape = MaterialTheme.shapes.small
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = MaterialTheme.shapes.small
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
                onValueChange = { event(EditProfileEvent.EditName(it)) },
                placeholder = R.string.username_hint
            )

            CustomTetField(
                modifier = Modifier.fillMaxWidth(),
                isSingleLine = false,
                value = uiState.profile.bio,
                onValueChange = { event(EditProfileEvent.EditBio(it)) },
                placeholder = R.string.user_bio_hint
            )

            SubmitButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { event(EditProfileEvent.OnUpdateProfileClick) },
            ) {
                Text(
                    text = stringResource(R.string.upload_changes_text),
                    style = MaterialTheme.typography.titleSmall
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
        event = {}
    )
}