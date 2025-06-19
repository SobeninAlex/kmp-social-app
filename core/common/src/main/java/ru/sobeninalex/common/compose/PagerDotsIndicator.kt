package ru.sobeninalex.common.compose

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.material.math.MathUtils
import ru.sobeninalex.resources.White87
import ru.sobeninalex.resources.WhiteColor50
import kotlin.math.abs

@Composable
fun PagerDotsIndicator(
    pagerState: PagerState,
    count: Int,
    modifier: Modifier = Modifier,
    pageWidth: Dp = 5.dp,
    activePageWidth: Dp = 100.dp,
    pageColor: Color = WhiteColor50,
    activePageColor: Color = White87
) {
    if (count < 2) return

    val currentPage = pagerState.currentPage % count
    val targetPage = if (pagerState.currentPageOffsetFraction < 0) {
        (pagerState.currentPage - 1) % count
    } else {
        (pagerState.currentPage + 1) % count
    }

    // размер точек
    val currentPageWidth: Dp
    val targetPageWidth: Dp
    if (pageWidth == activePageWidth) {
        currentPageWidth = pageWidth
        targetPageWidth = pageWidth
    } else {
        val activePageMultiplier = activePageWidth / pageWidth
        currentPageWidth =
            pageWidth * (1 + (1 - abs(pagerState.currentPageOffsetFraction)) * activePageMultiplier)
        targetPageWidth =
            pageWidth * (1 + abs(pagerState.currentPageOffsetFraction) * activePageMultiplier)
    }

    // цвет точек
    val currentPageColor = lerpColor(
        start = activePageColor,
        stop = pageColor,
        fraction = abs(pagerState.currentPageOffsetFraction)
    )
    val targetPageColor = lerpColor(
        start = activePageColor,
        stop = pageColor,
        fraction = 1 - abs(pagerState.currentPageOffsetFraction)
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(pageWidth),
        modifier = modifier
    ) {
        repeat(count) { index ->
            val dotWidth = when (index) {
                currentPage -> currentPageWidth
                targetPage -> targetPageWidth
                else -> pageWidth
            }
            val dotColor = when (index) {
                currentPage -> currentPageColor
                targetPage -> targetPageColor
                else -> pageColor
            }
            Box(
                modifier = Modifier
                    .height(pageWidth)
                    .width(dotWidth)
                    .clip(CircleShape)
                    .background(dotColor)
                    .animateContentSize()
            )
        }
    }
}

private fun lerpColor(
    start: Color,
    stop: Color,
    fraction: Float
): Color {
    val alpha = MathUtils.lerp(start.alpha, stop.alpha, fraction)
    val red = MathUtils.lerp(start.red, stop.red, fraction)
    val green = MathUtils.lerp(start.green, stop.green, fraction)
    val blue = MathUtils.lerp(start.blue, stop.blue, fraction)
    return Color(alpha = alpha, red = red, green = green, blue = blue)
}