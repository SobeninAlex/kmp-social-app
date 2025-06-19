package ru.sobeninalex.common.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MediaPager(
    onMediaClick: () -> Unit,
    mediaFilesUrls: List<String>,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.0f),
    ) {
        val state: PagerState = rememberPagerState(
            /*зациклинная прокрутка*/
            initialPage = mediaFilesUrls.size * 1000,
            pageCount = { if (mediaFilesUrls.size > 1) mediaFilesUrls.size * 2000 else 1 }
        )

        HorizontalPager(
            state = state,
            beyondViewportPageCount = 1,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            ImageCard(
                model = mediaFilesUrls.getOrNull(page % mediaFilesUrls.size),
                onClick = onMediaClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.0f),
            )
        }

        PagerDotsIndicator(
            pagerState = state,
            count = mediaFilesUrls.size,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 12.dp)
        )
    }
}