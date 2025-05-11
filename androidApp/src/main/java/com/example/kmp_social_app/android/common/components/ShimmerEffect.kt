package com.example.kmp_social_app.android.common.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.example.kmp_social_app.android.common.theme.BlackColor10
import com.example.kmp_social_app.android.common.theme.WhiteColor50

/**
 * waveColor:
 * на белой = BlackColor05
 * на любом другом = WhiteColor50
 */
fun Modifier.shimmerLinearGradient(): Modifier = composed {
    val waveColor: Color = if (isSystemInDarkTheme()) WhiteColor50 else BlackColor10
    val durationsMills = 1700
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation by transition.animateFloat(
        label = "",
        initialValue = -1.5f * size.width.toFloat(),
        targetValue = 1.5f * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationsMills,
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    drawBehind {
        drawRect(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color.Transparent,
                    waveColor,
                    waveColor,
                    Color.Transparent
                ),
                start = Offset(x = translateAnimation, y = 0f) ,
                end = Offset(x = translateAnimation + size.width.toFloat(), y = size.height.toFloat() / 3)
            )
        )
    }.onGloballyPositioned {
        size = it.size
    }
}

fun Modifier.shimmerHorizontalGradient(): Modifier = composed {
    val waveColor: Color = if (isSystemInDarkTheme()) WhiteColor50 else BlackColor10
    val durationsMills = 1700
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")

    val translateAnimation by transition.animateFloat(
        label = "",
        initialValue = -1.5f * size.width.toFloat(),
        targetValue = 1.5f * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationsMills,
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    drawBehind {
        drawRect(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color.Transparent,
                    waveColor,
                    waveColor,
                    Color.Transparent
                ),
                startX = translateAnimation ,
                endX = translateAnimation + size.width.toFloat()
            )
        )
    }.onGloballyPositioned {
        size = it.size
    }
}