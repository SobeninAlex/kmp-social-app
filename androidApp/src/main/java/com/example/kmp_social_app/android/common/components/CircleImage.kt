package com.example.kmp_social_app.android.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import com.example.kmp_social_app.android.R

@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    onClick: () -> Unit = {}
) {
    val placeholder = if (isSystemInDarkTheme()) {
        painterResource(R.drawable.img_mock_picture_dark)
    } else {
        painterResource(R.drawable.img_mock_picture_light)
    }

    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        placeholder = placeholder,
        error = placeholder,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(CircleShape)
            .clickable  { onClick() }
    )
}