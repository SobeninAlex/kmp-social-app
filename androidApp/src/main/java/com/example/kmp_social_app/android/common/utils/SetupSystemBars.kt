package com.example.kmp_social_app.android.common.utils

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat


fun Context.setupSystemBarStyle(
    statusBarColor: Color,
    navigationBarColor: Color,
    isLightStatusBar: Boolean,
) = with(this as ComponentActivity) {
    val statusBarStyle = if (!isLightStatusBar) {
        SystemBarStyle.dark(statusBarColor.toArgb())
    } else {
        SystemBarStyle.light(statusBarColor.toArgb(), Color.Black.toArgb())
    }

    val navigationBarStyle = when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> {
            SystemBarStyle.dark(navigationBarColor.toArgb())
        }
        else -> {
            SystemBarStyle.light(navigationBarColor.toArgb(), Color.Black.toArgb())
        }

    }

    enableEdgeToEdge(
        statusBarStyle = statusBarStyle,
        navigationBarStyle = navigationBarStyle
    )
}

fun Context.setupSystemBarStyleDefault(
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null
) = with(this as ComponentActivity) {
    val statusBarStyle: SystemBarStyle
    val navigationBarStyle: SystemBarStyle

    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> {
            statusBarStyle = SystemBarStyle.dark(statusBarColor?.toArgb() ?: Color.Black.toArgb())
            navigationBarStyle = SystemBarStyle.dark(navigationBarColor?.toArgb() ?: Color.Black.toArgb())
        }

        else -> {
            statusBarStyle =
                SystemBarStyle.light(statusBarColor?.toArgb() ?: Color.White.toArgb(), Color.Black.toArgb())
            navigationBarStyle =
                SystemBarStyle.light(navigationBarColor?.toArgb() ?: Color.White.toArgb(), Color.Black.toArgb())
        }
    }

    enableEdgeToEdge(
        statusBarStyle = statusBarStyle,
        navigationBarStyle = navigationBarStyle
    )
}

//fun Context.setupSystemBarStyleDark(
//    statusBarColor: Color = BackgroundCardColorDark,
//    navigationBarColor: Color = BackgroundColorDark
//) = with(this as ComponentActivity) {
//    enableEdgeToEdge(
//        statusBarStyle = SystemBarStyle.dark(statusBarColor.toArgb()),
//        navigationBarStyle = SystemBarStyle.dark(navigationBarColor.toArgb()),
//    )
//}
//
//fun Context.setupSystemBarStyleLight(
//    statusBarColor: Color = BackgroundCardColorLight,
//    navigationBarColor: Color = BackgroundColorLight
//) = with(this as ComponentActivity) {
//    enableEdgeToEdge(
//        statusBarStyle = SystemBarStyle.light(statusBarColor.toArgb(), BlackColor.toArgb()),
//        navigationBarStyle = SystemBarStyle.light(navigationBarColor.toArgb(), BlackColor.toArgb())
//    )
//}
//
////==================================================================================================
//
///**
// * window
// */
//@Composable
//fun SystemBarsColorChanger(
//    statusSystemBarColor: Color?,
//    navSystemBarColor: Color?,
//    isLightIcons: Boolean
//) {
//    val window = (LocalContext.current as Activity).window
//    val view = LocalView.current
//
//    SideEffect {
//        changeSystemBarsColor(
//            window = window,
//            view = view,
//            statusSystemBarColor = statusSystemBarColor,
//            navSystemBarColor = navSystemBarColor,
//            isLightIcons = isLightIcons
//        )
//    }
//}
//
fun changeSystemBarsColor(
    window: Window,
    statusBarColor: Color?,
    navBarColor: Color?,
    isLightIcons: Boolean
) {
    statusBarColor?.let {
        window.statusBarColor = it.toArgb()
    }

    navBarColor?.let {
        window.navigationBarColor = it.toArgb()
    }

    WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = !isLightIcons
}