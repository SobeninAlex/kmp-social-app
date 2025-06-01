package ru.sobeninalex.account.presentation.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
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
import ru.sobeninalex.common.compose.CircleImage
import ru.sobeninalex.common.compose.FollowButton
import ru.sobeninalex.common.models.profile.Profile
import ru.sobeninalex.resources.Body_Normal14
import ru.sobeninalex.resources.Body_Normal16
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Title_Bold16
import ru.sobeninalex.resources.Title_Bold18
import ru.sobeninalex.resources.roundedCornerShape20
import ru.sobeninalex.resources.roundedCornerShape4

fun LazyListScope.profileHeaderBlock(
    modifier: Modifier = Modifier,
    profile: Profile,
    followingOperation: Boolean,
    onFollowClick: () -> Unit,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onEditProfileClick: () -> Unit,
) = item {
    ProfileHeaderBlock(
        modifier = modifier.animateItem(),
        profile = profile,
        followingOperation = followingOperation,
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
    followingOperation: Boolean,
    onFollowClick: () -> Unit,
    onFollowersClick: () -> Unit,
    onFollowingClick: () -> Unit,
    onEditProfileClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(roundedCornerShape20)
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

            if (profile.isOwnProfile) {
                OutlinedButton(
                    onClick = onEditProfileClick,
                    shape = roundedCornerShape4,
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
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = profile.name,
                style = Title_Bold18,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = profile.bio,
                style = Body_Normal14,
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
                    withStyle(Title_Bold16.toSpanStyle()) {
                        append("${profile.followersCount} ")
                    }

                    withStyle(Body_Normal16.toSpanStyle()) {
                        append(stringResource(R.string.followers_text))
                    }
                },
                modifier = Modifier.clickable { onFollowersClick() }
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(Body_Normal16.toSpanStyle()) {
                        append("${profile.followingCount} ")
                    }

                    withStyle(Body_Normal16.toSpanStyle()) {
                        append(stringResource(R.string.following_text))
                    }
                },
                modifier = Modifier.clickable { onFollowingClick() }.weight(1f)
            )

            if (!profile.isOwnProfile) {
                FollowButton(
                    modifier = modifier.height(38.dp).width(110.dp),
                    followingOperation = followingOperation,
                    onClick = onFollowClick,
                    contentPadding = PaddingValues(horizontal = 28.dp),
                    text = if (profile.isFollowing) R.string.unfollow_text_label else R.string.follow_text_label,
                    isOutline = profile.isFollowing,
                )
            }

        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun ProfileHeaderBlockPreview() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileHeaderBlock(
                profile = Profile.Preview.copy(isOwnProfile = false, isFollowing = false),
                onFollowClick = {},
                onFollowersClick = {},
                onFollowingClick = {},
                onEditProfileClick = {},
                followingOperation = false
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun ProfileHeaderBlockPreviewDark() {
    ru.sobeninalex.resources.MainAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            ProfileHeaderBlock(
                profile = Profile.Preview,
                onFollowClick = {},
                onFollowersClick = {},
                onFollowingClick = {},
                onEditProfileClick = {},
                followingOperation = false
            )
        }
    }
}