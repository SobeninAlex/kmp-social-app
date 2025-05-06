package com.example.kmp_social_app.android.presentation.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.CustomTetField
import com.example.kmp_social_app.android.common.components.CustomTopBar
import com.example.kmp_social_app.android.common.components.LoadingDialog
import com.example.kmp_social_app.android.common.components.SubmitButton
import com.example.kmp_social_app.android.common.navigation.AuthGraph
import com.example.kmp_social_app.android.common.navigation.LocalNavController
import com.example.kmp_social_app.android.common.navigation.MainGraph
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignUpScreen() {
    val viewModel = koinViewModel<SignUpViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SignUpScreenContent(
        uiState = uiState,
        event = viewModel::onEvent
    )
}

@Composable
private fun SignUpScreenContent(
    uiState: SignUpUiState,
    event: (SignUpEvent) -> Unit
) {
    val navController = LocalNavController.current

    LaunchedEffect(uiState.isAuthSuccess) {
        if (uiState.isAuthSuccess) {
            navController.navigate(MainGraph.HomeRoute) {
                popUpTo(AuthGraph) {
                    inclusive = true
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.signup_destination_title)
            )
        }
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CustomTetField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.username,
                onValueChange = { event(SignUpEvent.InputUsername(it)) },
                placeholder = R.string.username_hint
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTetField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.email,
                onValueChange = { event(SignUpEvent.InputEmail(it)) },
                placeholder = R.string.email_hint,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTetField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password,
                onValueChange = { event(SignUpEvent.InputPassword(it)) },
                placeholder = R.string.password_hint,
                keyboardType = KeyboardType.Password,
                isPasswordTextField = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            SubmitButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { event(SignUpEvent.OnSignUpClick) },
                enabled = !uiState.isLoading
            ) {
                Text(text = stringResource(R.string.signup_button))
            }
        }
    }

    if (uiState.isLoading) {
        LoadingDialog()
    }
}

@Preview
@Composable
private fun SignUpScreenContentPreview() {
    SignUpScreenContent(
        uiState = SignUpUiState.Preview,
        event = {}
    )
}