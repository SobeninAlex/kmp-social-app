package ru.sobeninalex.common.compose


import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.resources.Caption_Medium12
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.White87
import ru.sobeninalex.resources.roundedCornerShape4

@Composable
fun FollowButton(
    modifier: Modifier = Modifier,
    followingOperation: Boolean = false,
    onClick: () -> Unit,
    @StringRes text: Int = R.string.follow_text_label,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    isOutline: Boolean = false
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = !followingOperation,
        contentPadding = contentPadding,
        shape = roundedCornerShape4,
        colors = if (isOutline) {
            ButtonDefaults.outlinedButtonColors()
        } else {
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        },
        border = if (isOutline) {
            BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)
        } else null,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp
        )
    ) {
        Box(
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Animate(
                visible = followingOperation,
                modifier = Modifier.align(Alignment.Center)
            ) {
                DotsLoadingIndicator()
            }

            Animate(
                visible = !followingOperation,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = stringResource(id = text),
                    style = Caption_Medium12,
                    color = if (isOutline) MaterialTheme.colorScheme.onBackground else White87
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun FollowButtonPreview() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            FollowButton(
                followingOperation = true,
                onClick = {},
                text = R.string.followers_text,
            )
        }
    }
}