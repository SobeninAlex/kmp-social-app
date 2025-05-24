package ru.sobeninalex.resources

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Blue = Color(0xFF0B26E0)
val Gray = Color(0xFFF3F3F4)

val Black = Color(0xFF000000)

val Black87 = Color(0xFF171717)
val DarkGray = Color(0xFF999A9A)

val Black54 = Color(0xFF373B3F)
val Black24 = Color(0xFF212023)

val White = Color(0xFFFFFFFF)

val White87 = Color(0xFFE2E2E2)
val LightGray = Color(0xFF8A8A8D)

val White36 = Color(0xFFE5E5E5)
val White76 = Color(0xFFF4F4F4)

val BlackColor10 = Color(0x1A11161C)
val WhiteColor50 = Color(0x80FFFFFF)
val GrayColor10 = Color(0xFFEDEFF0)
val InverseSurfaceLight = Color(0xFFE1E4E8)
val InverseSurfaceDark = Color(0xFF2C2B2D)

val LightColors = lightColorScheme(
    secondary = White,
    primary = Blue,
    primaryContainer = Blue,
    background = White76,
    onBackground = Black87,
    surface = White,
    onSurface = LightGray,
    inverseSurface = InverseSurfaceLight,
)

val DarkColors = darkColorScheme(
    secondary = Black24,
    primary = Blue,
    primaryContainer = Blue,
    background = Black87,
    onBackground = White87,
    surface = Black54,
    onSurface = DarkGray,
    inverseSurface = InverseSurfaceDark,
)