package com.example.kmp_social_app.android.presentation.post_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.SubmitButton

@Composable
fun CommentsBlock(
    modifier: Modifier = Modifier
) {

}

@Composable
private fun CommentsBlockHeader(
    modifier: Modifier = Modifier,
    onAddCommentClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.comments_label),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        SubmitButton(
            onClick = onAddCommentClick
        ) {
            Text(
                text = stringResource(R.string.add_comment_button_label)
            )
        }
    }
}