package com.example.kmp_social_app.android.presentation.account.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kmp_social_app.android.common.components.PostListItem
import com.example.kmp_social_app.feature.post.domain.model.Post

fun LazyListScope.profilePostsBlock(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    posts: List<Post>,
    onPostClick: (Post) -> Unit,
    onLikeClick: (Post) -> Unit,
    onCommentClick: (Post) -> Unit,
) {
    if (isLoading) {
        item {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        return
    }

    if (posts.isEmpty()) {
        item {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Empty",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        return
    }

    items(
        items = posts,
        key = { it.postId }
    ) { post ->
        PostListItem(
            modifier = modifier,
            post = post,
            onPostClick = { onPostClick(post) },
            onProfileClick = {},
            onLikeClick = { onLikeClick(post) },
            onCommentClick = { onCommentClick(post) },
        )
    }
}