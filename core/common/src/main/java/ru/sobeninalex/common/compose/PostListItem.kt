package ru.sobeninalex.common.compose

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sobeninalex.resources.R
import ru.sobeninalex.domain.features.post.model.Post

@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onPostClick: () -> Unit,
    onProfileClick: (String) -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    isDetailScreen: Boolean = false,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondary)
            .clickable { onPostClick() }
    ) {
        PostItemHeader(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            name = post.userName,
            avatar = post.userAvatar,
            date = post.createdAt,
            onProfileClick = { onProfileClick(post.userId) }
        )

        ImageCard(
            onClick = onPostClick,
            model = post.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f),
        )

        PostLikesRow(
            likesCount = post.likesCount,
            commentsCount = post.commentsCount,
            isLiked = post.isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            enabledLike = post.enabledLike
        )

        Text(
            text = post.caption,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            maxLines = if (isDetailScreen) 50 else 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun PostListItemShimmer(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.secondary)
            .shimmerLinearGradient()
    ) {
        PostItemHeaderShimmer(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
                .background(MaterialTheme.colorScheme.inverseSurface)
                .shimmerLinearGradient()
        )

        Spacer(modifier = Modifier.height(14.dp))

        PostLikesRowShimmer(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .height(40.dp)
                .clip(shape = MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.inverseSurface)
                .shimmerLinearGradient()
        )
    }
}

@Composable
private fun PostItemHeader(
    modifier: Modifier = Modifier,
    name: String,
    avatar: String?,
    date: String,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CircleImage(
            imageUrl = avatar,
            modifier = Modifier.size(30.dp),
            onClick = onProfileClick
        )

        Text(
            text = name,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Box(
            modifier = Modifier
                .size(4.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSurface)
        )

        Text(
            text = date,
            style = MaterialTheme.typography.labelMedium.copy(
                textAlign = TextAlign.Start,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.weight(1f)
        )

        Icon(
            painter = painterResource(R.drawable.round_more_horiz_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun PostItemHeaderShimmer(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape)
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
                .fillMaxWidth(0.5f)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.inverseSurface)
                .shimmerLinearGradient()
        )
    }
}

@Composable
private fun PostLikesRow(
    modifier: Modifier = Modifier,
    likesCount: Int,
    commentsCount: Int,
    isLiked: Boolean,
    enabledLike: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onLikeClick,
            enabled = enabledLike,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(
                painter = if (isLiked) {
                    painterResource(R.drawable.like_icon_filled)
                } else {
                    painterResource(R.drawable.like_icon_outlined)
                },
                contentDescription = null,
            )
        }

        Text(
            text = "$likesCount",
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = onCommentClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.chat_icon_outlined),
                contentDescription = null,
            )
        }

        Text(
            text = "$commentsCount",
            style = MaterialTheme.typography.titleSmall.copy(fontSize = 18.sp),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun PostLikesRowShimmer(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(height = 26.dp, width = 42.dp)
                .clip(shape = MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.inverseSurface)
                .shimmerLinearGradient()
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(height = 26.dp, width = 42.dp)
                .clip(shape = MaterialTheme.shapes.small)
                .background(color = MaterialTheme.colorScheme.inverseSurface)
                .shimmerLinearGradient()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostListItemPreview() {
    ru.sobeninalex.resources.KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PostListItem(
                post = Post.Preview,
                onPostClick = {},
                onProfileClick = {},
                onLikeClick = {},
                onCommentClick = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostListItemShimmerPreview() {
    ru.sobeninalex.resources.KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PostListItemShimmer()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostListItemPreviewDark() {
    ru.sobeninalex.resources.KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PostListItem(
                post = Post.Preview,
                onPostClick = {},
                onProfileClick = {},
                onLikeClick = {},
                onCommentClick = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostListItemShimmerPreviewDark() {
    ru.sobeninalex.resources.KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PostListItemShimmer()
        }
    }
}