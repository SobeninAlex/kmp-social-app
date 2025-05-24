package ru.sobeninalex.utils.compose

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.sobeninalex.resources.DarkGray
import kotlinx.coroutines.delay

@Composable
fun DotsLoadingIndicator(
    modifier: Modifier = Modifier,
    dotsColor: Color = ru.sobeninalex.resources.DarkGray,
    dotsSize: Dp = 8.dp,
    spaceBetween: Dp = 4.dp,
    travelDistance: Dp = 12.dp
) {
    val dots = listOf(
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) },
        remember { Animatable(initialValue = 0f) }
    )
    dots.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    keyframes {
                        durationMillis = 1200
                        0f at 0 using LinearOutSlowInEasing
                        1f at 300 using LinearOutSlowInEasing
                        0f at 600 using LinearOutSlowInEasing
                        0f at 1200 using LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    val distance = with(LocalDensity.current) { travelDistance.toPx() }
    Row(
        horizontalArrangement = Arrangement.spacedBy(spaceBetween),
        modifier = modifier
    ) {
        dots.map { it.value }.forEach { value ->
            Box(
                modifier = Modifier
                    .size(dotsSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(color = dotsColor, shape = CircleShape)
            )
        }
    }
}