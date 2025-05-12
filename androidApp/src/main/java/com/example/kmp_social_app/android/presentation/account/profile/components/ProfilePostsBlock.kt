package com.example.kmp_social_app.android.presentation.account.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.kmp_social_app.android.common.components.PostListItem
import com.example.kmp_social_app.android.common.components.SubmitButton
import com.example.kmp_social_app.feature.post.domain.model.Post

fun LazyListScope.profilePostsBlock(
    isLoading: Boolean,
    posts: List<Post>,
    onPostClick: (Post) -> Unit,
    onLikeClick: (Post) -> Unit,
    onCommentClick: (Post) -> Unit,
    isOwnProfile: Boolean,
) {
    if (posts.isEmpty() && isOwnProfile && !isLoading) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItem(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "You don't have any posts",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                )

                Spacer(modifier = Modifier.height(12.dp))

                SubmitButton(
                    onClick = { /*create post*/ }
                ) {
                    Text(
                        text = "create post",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    } else {
        items(
            items = posts,
            key = { it.postId }
        ) { post ->
            PostListItem(
                modifier = Modifier.animateItem(),
                post = post,
                onPostClick = { onPostClick(post) },
                onProfileClick = {},
                onLikeClick = { onLikeClick(post) },
                onCommentClick = { onCommentClick(post) },
            )
        }
    }
}