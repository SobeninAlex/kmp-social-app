package com.example.kmp_social_app.android.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kmp_social_app.android.R
import com.example.kmp_social_app.android.common.theme.KmpSocialAppTheme
import com.example.kmp_social_app.feature.follows.domain.model.FollowUser

@Composable
fun OnBoardingBlock(
    modifier: Modifier = Modifier,
    users: List<FollowUser>,
    onUserClick: (FollowUser) -> Unit,
    onFollowButtonClick: (FollowUser) -> Unit,
    onBoardingFinishClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.onboarding_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.onboarding_guidance_subtitle),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        UsersRow(
            users = users,
            onUserClick = onUserClick,
            onFollowButtonClick = onFollowButtonClick
        )

        OutlinedButton(
            onClick = onBoardingFinishClick,
            shape = MaterialTheme.shapes.small,
            border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth(fraction = 0.5f)
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = stringResource(R.string.onboarding_button_text),
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun UsersRow(
    modifier: Modifier = Modifier,
    users: List<FollowUser>,
    onUserClick: (FollowUser) -> Unit,
    onFollowButtonClick: (FollowUser) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        modifier = modifier
    ) {
        items(
            items = users,
            key = { it.id }
        ) { user ->
            OnBoardingUserCard(
                modifier = Modifier.animateItem(),
                followUser = user,
                onUserClick = { onUserClick(user) },
                onFollowClick = { onFollowButtonClick(user) }
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun OnBoardingBlockPreview() {
    KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            OnBoardingBlock(
                users = FollowUser.PreviewFollowUserList,
                onUserClick = {},
                onFollowButtonClick = {},
                onBoardingFinishClick = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun OnBoardingBlockPreviewDark() {
    KmpSocialAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            OnBoardingBlock(
                users = FollowUser.PreviewFollowUserList,
                onUserClick = {},
                onFollowButtonClick = {},
                onBoardingFinishClick = {}
            )
        }
    }
}