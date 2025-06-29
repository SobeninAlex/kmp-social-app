package ru.sobeninalex.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import ru.sobeninalex.resources.R

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    model: Any?,
    onClick: () -> Unit = {},
    placeholder: Painter = if (isSystemInDarkTheme()) {
        painterResource(R.drawable.img_mock_picture_dark)
    } else {
        painterResource(R.drawable.img_mock_picture_light)
    },
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Crop,
    overlayColor: Color = Color.Transparent,
) {
    var isLoading by remember { mutableStateOf(false) }
    var isSuccess by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.clickable { onClick() }
    ) {
        AsyncImage(
            model = model,
            modifier = Modifier.fillMaxSize(),
            contentDescription = null,
            placeholder = placeholder,
            error = placeholder,
            onLoading = { isLoading = true },
            onSuccess = {
                isLoading = false
                isSuccess = true
            },
            onError = { isLoading = false },
            alignment = alignment,
            contentScale = contentScale,
        )

        if (isSuccess) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(overlayColor)
            )
        }

        if (isLoading) {
            DotsLoadingIndicator()
        }
    }
}