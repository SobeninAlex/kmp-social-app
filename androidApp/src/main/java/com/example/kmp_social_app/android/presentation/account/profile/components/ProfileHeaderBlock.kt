package com.example.kmp_social_app.android.presentation.account.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.components.CircleImage
import com.example.kmp_social_app.android.common.components.FollowButton
import com.example.kmp_social_app.android.common.theme.KmpSocialAppTheme
import com.example.kmp_social_app.feature.profile.domain.model.Profile

fun LazyListScope.profileHeaderBlock(
    modifier: Modifier = Modifier,
    profile: Profile,
    onFollowClick: () -> Unit,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onEditProfileClick: () -> Unit,
) = item {
    ProfileHeaderBlock(
        modifier = modifier,
        profile = profile,
        onFollowClick = onFollowClick,
        onFollowersClick = onFollowersClick,
        onFollowingClick = onFollowingClick,
        onEditProfileClick = onEditProfileClick
    )
}

@Composable
fun ProfileHeaderBlock(
    modifier: Modifier = Modifier,
    profile: Profile,
    onFollowClick: () -> Unit,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onEditProfileClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CircleImage(
                imageUrl = profile.avatar,
                modifier = Modifier.size(100.dp)
            )

            OutlinedButton(
                onClick = onEditProfileClick,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.size(30.dp),
                contentPadding = PaddingValues(6.dp),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
            ) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = profile.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = profile.bio,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle((MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)).toSpanStyle()) {
                        append("${profile.followersCount} ")
                    }

                    withStyle((MaterialTheme.typography.bodyMedium).toSpanStyle()) {
                        append(stringResource(R.string.followers_text))
                    }
                },
                modifier = Modifier.clickable { onFollowersClick() }
            )

            Text(
                text = buildAnnotatedString {
                    withStyle((MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp)).toSpanStyle()) {
                        append("${profile.followingCount} ")
                    }

                    withStyle((MaterialTheme.typography.bodyMedium).toSpanStyle()) {
                        append(stringResource(R.string.following_text))
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .clickable { onFollowingClick() }
            )

            FollowButton(
                onClick = onFollowClick,
                contentPadding = PaddingValues(horizontal = 28.dp),
                modifier = Modifier.height(34.dp),
                text = R.string.follow_text_label,
                isOutline = profile.isOwnProfile || profile.isFollowing,
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun ProfileHeaderBlockPreview() {
    KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileHeaderBlock(
                profile = Profile.Preview,
                onFollowClick = {},
                onFollowersClick = {},
                onFollowingClick = {},
                onEditProfileClick = {},
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun ProfileHeaderBlockPreviewDark() {
    KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileHeaderBlock(
                profile = Profile.Preview,
                onFollowClick = {},
                onFollowersClick = {},
                onFollowingClick = {},
                onEditProfileClick = {},
            )
        }
    }
}