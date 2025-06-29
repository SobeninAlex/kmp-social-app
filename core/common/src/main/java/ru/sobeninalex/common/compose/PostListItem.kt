package ru.sobeninalex.common.compose

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.common.models.post.Post
import ru.sobeninalex.resources.Body_Normal14
import ru.sobeninalex.resources.Caption_Medium12
import ru.sobeninalex.resources.MainAppTheme
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Title_Bold16
import ru.sobeninalex.resources.Title_Bold18
import ru.sobeninalex.resources.roundedCornerShape4

@Composable
fun PostListItem(
    modifier: Modifier = Modifier,
    post: Post,
    onPostClick: () -> Unit,
    onProfileClick: (String) -> Unit,
    onDeleteClick: () -> Unit = {},
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
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .height(42.dp),
            name = post.userName,
            avatar = post.userAvatar,
            date = post.createdAt,
            isOwnPost = post.isOwnPost,
            isDetailScreen = isDetailScreen,
            onDeleteClick = onDeleteClick,
            isDeletingPost = post.isDeletingPost,
            onProfileClick = { onProfileClick(post.userId) }
        )

        MediaPager(
            onMediaClick = onPostClick,
            mediaFilesUrls = post.imageUrls,
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
            style = Body_Normal14,
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
                .clip(roundedCornerShape4)
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
    isOwnPost: Boolean,
    onProfileClick: () -> Unit,
    onDeleteClick: () -> Unit,
    isDetailScreen: Boolean,
    isDeletingPost: Boolean,
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
            style = Title_Bold16,
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
            style = Caption_Medium12.copy(
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.weight(1f)
        )

        if (isOwnPost && !isDetailScreen) {
            Box(
                modifier = Modifier.size(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Animate(
                    visible = isDeletingPost
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp).padding(4.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Animate(
                    visible = !isDeletingPost
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_delete_24),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { onDeleteClick() }
                            .padding(4.dp)
                    )
                }
            }
        }
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
                .clip(roundedCornerShape4)
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
            style = Title_Bold18,
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
            style = Title_Bold18,
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
                .clip(roundedCornerShape4)
                .background(MaterialTheme.colorScheme.inverseSurface)
                .shimmerLinearGradient()
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .size(height = 26.dp, width = 42.dp)
                .clip(roundedCornerShape4)
                .background(color = MaterialTheme.colorScheme.inverseSurface)
                .shimmerLinearGradient()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostListItemPreview() {
    MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PostListItem(
                post = Post.Preview.copy(isOwnPost = true),
                onPostClick = {},
                onProfileClick = {},
                onLikeClick = {},
                onCommentClick = {},
                onDeleteClick = {},
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostListItemShimmerPreview() {
    MainAppTheme {
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
    MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PostListItem(
                post = Post.Preview,
                onPostClick = {},
                onProfileClick = {},
                onLikeClick = {},
                onDeleteClick = {},
                onCommentClick = {},
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun PostListItemShimmerPreviewDark() {
    MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            PostListItemShimmer()
        }
    }
}