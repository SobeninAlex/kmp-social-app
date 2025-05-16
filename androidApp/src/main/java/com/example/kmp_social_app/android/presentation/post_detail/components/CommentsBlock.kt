package com.example.kmp_social_app.android.presentation.post_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.CommentListItem
import com.example.kmp_social_app.android.common.components.SubmitButton
import com.example.kmp_social_app.feature.post.domain.model.PostComment


fun LazyListScope.postDetailCommentsBlock(
    isLoading: Boolean,
    onAddCommentClick: () -> Unit,
    comments: List<PostComment>,
    onProfileClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    item {
        CommentsBlockHeader(
            modifier = Modifier.animateItem(),
            onAddCommentClick = onAddCommentClick
        )
    }

    if (comments.isEmpty() && !isLoading) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center,
                    text = "Empty...",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    } else {
        itemsIndexed(
            items = comments,
            key = { _, item -> item.commentId }
        ) { _, item ->
            CommentListItem(
                modifier = Modifier.animateItem(),
                comment = item,
                onProfileClick = onProfileClick,
                onDeleteClick = onDeleteClick
            )

            Spacer(modifier = Modifier.height(8.dp))
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

