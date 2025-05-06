package com.example.kmp_social_app.android.presentation.post_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.CommentListItem
import com.example.kmp_social_app.android.common.components.HorizontalLine
import com.example.kmp_social_app.android.common.components.SubmitButton
import com.example.kmp_social_app.feature.comments.domain.model.Comment


fun LazyListScope.postDetailCommentsBlock(
    isLoading: Boolean,
    onAddCommentClick: () -> Unit,
    comments: List<Comment>,
    onProfileClick: () -> Unit,
    onMoreIconClick: () -> Unit
) {
    item {
        CommentsBlockHeader(
            onAddCommentClick = onAddCommentClick
        )
    }

    if (isLoading) {
        item {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    } else {
        itemsIndexed(
            items = comments,
            key = { _, item -> item.id }
        ) { index, item ->
            HorizontalLine(index > 0)
            CommentListItem(
                comment = item,
                onProfileClick = onProfileClick,
                onMoreIconClick = onMoreIconClick
            )
        }
    }


}

@Composable
fun CommentsBlockHeader(
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