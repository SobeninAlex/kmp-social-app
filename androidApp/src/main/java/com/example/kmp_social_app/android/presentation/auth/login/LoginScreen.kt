package com.example.kmp_social_app.android.presentation.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.CustomTetField
import com.example.kmp_social_app.android.common.components.CustomTopBar
import com.example.kmp_social_app.android.common.components.LoadingDialog
import com.example.kmp_social_app.android.common.navigation.LocalNavController
import com.example.kmp_social_app.android.common.components.SubmitButton
import com.example.kmp_social_app.android.common.navigation.AuthGraph
import com.example.kmp_social_app.android.common.navigation.MainGraph
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen() {
    val viewModel = koinViewModel<LoginViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LoginScreenContent(
        uiState = uiState,
        action = viewModel::onAction
    )
}

@Composable
private fun LoginScreenContent(
    uiState: LoginUiState,
    action: (LoginAction) -> Unit
) {
    val navController = LocalNavController.current

    LaunchedEffect(uiState.isLoginSuccess) {
        if (uiState.isLoginSuccess) {
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
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.login_destination_title)
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
                value = uiState.email,
                onValueChange = { action(LoginAction.InputEmail(it)) },
                placeholder = R.string.email_hint,
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTetField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.password,
                onValueChange = { action(LoginAction.InputPassword(it)) },
                placeholder = R.string.password_hint,
                keyboardType = KeyboardType.Password,
                isPasswordTextField = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            SubmitButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { action(LoginAction.OnLoginClick) },
                enabled = !uiState.isLoading
            ) {
                Text(text = stringResource(R.string.login_button_label))
            }

            Spacer(modifier = Modifier.height(24.dp))
            TextButton(
                onClick = {
                    navController.navigate(AuthGraph.SignUpRoute)
                }
            ) {
                Text(text = stringResource(R.string.to_create_account))
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
    LoginScreenContent(
        uiState = LoginUiState.Preview,
        action = {}
    )
}