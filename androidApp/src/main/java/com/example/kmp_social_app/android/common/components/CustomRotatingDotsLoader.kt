package com.example.kmp_social_app.android.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CustomRotatingDotsLoader(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    colors: List<Color> = listOf(
        Color(0xFFFF1200), //Red
        Color(0xFF14B91E), //Green
        Color(0xFF0B26E0), //Blue
        Color(0xFFFFBF00)  //Yellow
    ),
    dotRadius: Dp = 6.dp,
    minOrbitRadius: Dp = 10.dp,
    maxOrbitRadius: Dp = 25.dp,
    rotationDurationMillis: Int = 1000, //Длительность одного полного оборота
    pulsationDurationMillis: Int = 500 //Длительность одного цикла смыкания-размыкания
) {
    val transition = rememberInfiniteTransition(label = "dotsLoaderTransition")

    //Анимация для общего вращения
    val rotationAngle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = rotationDurationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotationAngle"
    )

    //Анимация для изменения радиуса орбиты (смыкание/размыкание)
    val currentOrbitRadiusPx by transition.animateFloat(
        initialValue = with(LocalDensity.current) { minOrbitRadius.toPx() },
        targetValue = with(LocalDensity.current) { maxOrbitRadius.toPx() },
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = pulsationDurationMillis, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orbitRadius"
    )

    val dotRadiusPx = with(LocalDensity.current) { dotRadius.toPx() }
    val numDots = colors.size

    AnimatedVisibility(
        modifier = modifier,
        visible = isLoading,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        Canvas(modifier = Modifier.size((maxOrbitRadius + dotRadius) * 2)) {
            val center = Offset(size.width / 2, size.height / 2)

            colors.forEachIndexed { index, color ->
                //Базовый угол для каждой точки, чтобы они были равномерно распределены
                val baseAngleOffset = (360f / numDots) * index
                //Текущий угол точки с учетом общего вращения
                val currentDotAngleDegrees = (rotationAngle + baseAngleOffset) % 360f
                val currentDotAngleRadians = Math.toRadians(currentDotAngleDegrees.toDouble())

                val x = center.x + (currentOrbitRadiusPx * cos(currentDotAngleRadians)).toFloat()
                val y = center.y + (currentOrbitRadiusPx * sin(currentDotAngleRadians)).toFloat()

                drawCircle(
                    color = color,
                    radius = dotRadiusPx,
                    center = Offset(x, y)
                )
            }
        }
    }
}