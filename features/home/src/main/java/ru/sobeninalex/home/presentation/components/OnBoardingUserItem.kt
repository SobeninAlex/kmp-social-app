package ru.sobeninalex.home.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.common.compose.CircleImage
import ru.sobeninalex.common.compose.FollowButton
import ru.sobeninalex.resources.R
import ru.sobeninalex.domain.features.follows.model.FollowUser

@Composable
fun OnBoardingUserCard(
    modifier: Modifier = Modifier,
    followUser: FollowUser,
    onUserClick: () -> Unit,
    onFollowClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .width(130.dp)
            .clickable { onUserClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircleImage(
                modifier = Modifier.size(50.dp),
                imageUrl = followUser.avatar
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = followUser.name,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            FollowButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(30.dp),
                isOutline = followUser.isFollowing,
                text = if (followUser.isFollowing) R.string.unfollow_text_label else R.string.follow_text_label,
                onClick = { onFollowClick() }
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun OnBoardingUserCardPreview() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            OnBoardingUserCard(
                followUser = FollowUser.Preview,
                onUserClick = {},
                onFollowClick = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun OnBoardingUserCardPreviewDark() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            OnBoardingUserCard(
                followUser = FollowUser.Preview,
                onUserClick = {},
                onFollowClick = {}
            )
        }
    }
}