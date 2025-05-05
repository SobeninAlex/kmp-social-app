package com.example.kmp_social_app.android.common.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.theme.KmpSocialAppTheme
import com.example.kmp_social_app.feature.post.domain.model.Post

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

        AsyncImage(
            model = post.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f),
            contentDescription = null,
            placeholder = if (isSystemInDarkTheme()) {
                painterResource(R.drawable.img_mock_picture_dark)
            } else {
                painterResource(R.drawable.img_mock_picture_light)
            },
            contentScale = ContentScale.Crop
        )

        PostLikesRow(
            likesCount = post.likesCount,
            commentsCount = post.commentsCount,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick
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
private fun PostLikesRow(
    modifier: Modifier = Modifier,
    likesCount: Int,
    commentsCount: Int,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onLikeClick
        ) {
            Icon(
                painter = painterResource(R.drawable.like_icon_outlined),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = "$likesCount",
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 18.sp
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = onCommentClick
        ) {
            Icon(
                painter = painterResource(R.drawable.chat_icon_outlined),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        Text(
            text = "$commentsCount",
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 18.sp
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostListItemPreview() {
    KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PostListItem(
                post = Post.Fake,
                onPostClick = {},
                onProfileClick = {},
                onLikeClick = {},
                onCommentClick = {}
            )
        }
    }
}