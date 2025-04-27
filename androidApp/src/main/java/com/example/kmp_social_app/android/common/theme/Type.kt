package com.example.kmp_social_app.android.common.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.kmp_social_app.android.R

val Lexend = FontFamily(
    Font(R.font.lexend_medium, FontWeight.Medium),
    Font(R.font.lexend_semi_bold, FontWeight.SemiBold),
    Font(R.font.lexend_bold, FontWeight.Bold)
)
val OpenSans = FontFamily(
    Font(R.font.open_sans_light, FontWeight.Light),
    Font(R.font.open_sans_regular, FontWeight.Normal)
)

val Typography = Typography(
    headlineMedium = TextStyle( //h6
        fontFamily = Lexend,
        fontWeight = FontWeight.Bold,
        fontSize = 21.sp
    ),
    titleMedium = TextStyle( //subtitle1
        fontFamily = Lexend,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle( //subtitle2
        fontFamily = Lexend,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle( //body1
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle( //body2
        fontFamily = OpenSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle( //button
        fontFamily = Lexend,
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp
    ),
    labelMedium = TextStyle( //caption
            fontFamily = OpenSans
    ),
    labelSmall = TextStyle( //overline
        fontFamily = OpenSans
    )
)