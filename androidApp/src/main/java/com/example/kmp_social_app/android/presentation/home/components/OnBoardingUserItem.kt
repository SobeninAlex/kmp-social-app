package com.example.kmp_social_app.android.presentation.home.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.CircleImage
import com.example.kmp_social_app.android.common.components.FollowButton
import com.example.kmp_social_app.android.common.theme.KmpSocialAppTheme
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser

@Composable
fun OnBoardingUserCard(
    modifier: Modifier = Modifier,
    followUser: FollowUser,
    isFollowing: Boolean = true,
    onUserClick: () -> Unit,
    onFollowClick: (Boolean) -> Unit
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .width(130.dp)
            .clickable { onUserClick() }
            .background(color = MaterialTheme.colorScheme.secondary)
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
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            FollowButton(
                modifier = Modifier.fillMaxWidth().heightIn(30.dp),
                isOutline = isFollowing,
                text = if (isFollowing) R.string.unfollow_text_label else R.string.follow_text_label,
                onClick = { onFollowClick(!isFollowing) }
            )
        }
    }
}

@Preview
@Composable
private fun OnBoardingUserCardPreview() {
    KmpSocialAppTheme {
        OnBoardingUserCard(
            followUser = FollowUser.Preview,
            onUserClick = {},
            onFollowClick = {}
        )
    }
}