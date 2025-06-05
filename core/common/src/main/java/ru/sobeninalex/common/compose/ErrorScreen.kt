package ru.sobeninalex.common.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Title_Bold18
import ru.sobeninalex.resources.roundedCornerShape4

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    visibility: Boolean,
    errorMessage: String?,
    onClick: () -> Unit
) {
    Animate(
        visible = visibility,
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (errorMessage.isNullOrEmpty()) stringResource(R.string.loading_error_message) else errorMessage,
                style = Title_Bold18,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onClick,
                shape = roundedCornerShape4,
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