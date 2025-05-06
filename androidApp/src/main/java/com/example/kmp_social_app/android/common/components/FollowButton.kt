package com.example.kmp_social_app.android.common.components

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.theme.Black87

@Composable
fun FollowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes text: Int = R.string.follow_text_label,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    isOutline: Boolean = false
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        contentPadding = contentPadding,
        shape = MaterialTheme.shapes.small,
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
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 12.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}