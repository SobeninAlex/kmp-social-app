package com.example.kmp_social_app.android.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.presentation.account.follows.FollowsAction

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    visibility: Boolean,
    errorMessage: String?,
    onClick: () -> Unit
) {
    AnimatedVisibility(
        visible = visibility,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (errorMessage.isNullOrEmpty()) stringResource(R.string.loading_error_message) else errorMessage,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onClick,
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxWidth(fraction = 0.5f)
            ) {
                Text(
                    text = stringResource(R.string.retry_button_text),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}