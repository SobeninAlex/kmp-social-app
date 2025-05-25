package ru.sobeninalex.common.compose

import android.net.Uri
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

@Composable
fun CircleImage(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    uri: Uri = Uri.EMPTY,
    onClick: () -> Unit = {}
) {
    ImageCard(
        model = if (uri == Uri.EMPTY) imageUrl else uri,
        onClick = onClick,
        modifier = modifier.clip(CircleShape)
    )
}