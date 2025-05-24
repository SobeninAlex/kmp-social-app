package ru.sobeninalex.common.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.domain.features.post.model.PostComment
import ru.sobeninalex.resources.R

@Composable
fun CommentListItem(
    modifier: Modifier = Modifier,
    comment: PostComment,
    onProfileClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CircleImage(
            imageUrl = comment.avatar,
            modifier = Modifier.size(30.dp),
            onClick = onProfileClick
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = comment.userName,
                    style = MaterialTheme.typography.titleSmall,
                )

                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.onSurface)
                )

                Text(
                    text = comment.createdAt,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                if (comment.isOwnComment) {
                    if (comment.isDeletingComment) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.outline_delete_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.clickable { onDeleteClick() }
                        )
                    }
                }

            }

            Text(
                text = comment.content,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun CommentListItemShimmer(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(color = MaterialTheme.colorScheme.secondary)
            .shimmerLinearGradient()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.inverseSurface)
                .shimmerLinearGradient()
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .height(14.dp)
                        .width(50.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inverseSurface)
                        .shimmerLinearGradient()
                )

                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.inverseSurface)
                        .shimmerLinearGradient()
                )

                Box(
                    modifier = Modifier
                        .height(14.dp)
                        .width(120.dp)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colorScheme.inverseSurface)
                        .shimmerLinearGradient()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.inverseSurface)
                    .shimmerLinearGradient()
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun CommentListItemPreview() {
    ru.sobeninalex.resources.KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CommentListItem(
                comment = PostComment.Preview,
                onProfileClick = {},
                onDeleteClick = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun CommentListItemShimmerPreview() {
    ru.sobeninalex.resources.KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CommentListItemShimmer()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun CommentListItemPreviewDark() {
    ru.sobeninalex.resources.KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CommentListItem(
                comment = PostComment.Preview,
                onProfileClick = {},
                onDeleteClick = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun CommentListItemShimmerPreviewDark() {
    ru.sobeninalex.resources.KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            CommentListItemShimmer()
        }
    }
}