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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.common.models.follow.FollowUser
import ru.sobeninalex.resources.Body_Normal14
import ru.sobeninalex.resources.Title_Bold16
import ru.sobeninalex.resources.roundedCornerShape20
import ru.sobeninalex.resources.roundedCornerShape4

@Composable
fun FollowUserListItem(
    modifier: Modifier = Modifier,
    followUser: FollowUser,
    onItemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(roundedCornerShape20)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable { onItemClick() }
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircleImage(
            modifier = Modifier.size(40.dp),
            imageUrl = followUser.avatar
        )

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = followUser.name,
                style = Title_Bold16,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = followUser.bio,
                style = Body_Normal14,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun FollowUserListItemShimmer(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(roundedCornerShape20)
            .background(MaterialTheme.colorScheme.secondary)
            .shimmerLinearGradient()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.inverseSurface)
                .shimmerLinearGradient()
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .height(14.dp)
                    .fillMaxWidth(0.3f)
                    .clip(roundedCornerShape4)
                    .background(MaterialTheme.colorScheme.inverseSurface)
                    .shimmerLinearGradient()
            )

            Box(
                modifier = Modifier
                    .height(14.dp)
                    .fillMaxWidth()
                    .clip(roundedCornerShape4)
                    .background(MaterialTheme.colorScheme.inverseSurface)
                    .shimmerLinearGradient()
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun FollowUserListItemPreview() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            FollowUserListItem(
                followUser = FollowUser.Preview,
                onItemClick = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun FollowUserListItemShimmerPreview() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            FollowUserListItemShimmer()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun FollowUserListItemPreviewDark() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            FollowUserListItem(
                followUser = FollowUser.Preview,
                onItemClick = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun FollowUserListItemShimmerPreviewDark() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            FollowUserListItemShimmer()
        }
    }
}