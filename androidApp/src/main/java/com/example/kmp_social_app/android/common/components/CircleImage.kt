package com.example.kmp_social_app.android.common.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    onClick: () -> Unit = {}
) {
    ImageCard(
        model = imageUrl,
        onClick = onClick,
        modifier = modifier.clip(CircleShape)
    )
}