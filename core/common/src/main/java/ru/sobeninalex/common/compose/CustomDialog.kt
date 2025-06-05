package ru.sobeninalex.common.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.sobeninalex.resources.Caption_Medium12
import ru.sobeninalex.resources.DarkGray
import ru.sobeninalex.resources.MainAppTheme
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Title_Bold14
import ru.sobeninalex.resources.Title_Bold16
import ru.sobeninalex.resources.roundedCornerShape20

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier
                .clip(roundedCornerShape20)
                .background(MaterialTheme.colorScheme.secondary)
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 12.dp
                ),
        ) {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                style = Title_Bold16
            )

            subtitle?.let {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = Caption_Medium12
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onDismissRequest,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = DarkGray
                    ),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = Title_Bold14,
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                TextButton(
                    onClick = onConfirmClick,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.confirm),
                        style = Title_Bold14,
                    )
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun DeletePostDialogPreview() {
    MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CustomDialog(
                onDismissRequest = {},
                onConfirmClick = {},
                title = "Title Title",
                subtitle = null
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun DeletePostDialogPreviewDark() {
    MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CustomDialog(
                onDismissRequest = {},
                onConfirmClick = {},
                title = "Title Title",
                subtitle = "subtitle subtitle subtitle"
            )
        }
    }
}