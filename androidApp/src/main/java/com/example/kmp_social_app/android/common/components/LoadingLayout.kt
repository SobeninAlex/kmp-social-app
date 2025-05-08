package com.example.kmp_social_app.android.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@Composable
fun LoadingLayout(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    animationMillis: Long = 0,
    loadingContent: @Composable BoxScope.() -> Unit = {
        Box(modifier = modifier.fillMaxSize()) {
            CustomRotatingDotsLoader(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    },
    content: @Composable BoxScope.() -> Unit
) {
    var animationStarted by remember { mutableStateOf(false) }

    val showLoading = isLoading || animationStarted
    LaunchedEffect(key1 = showLoading) {
        animationStarted = isLoading
        delay(animationMillis)
        animationStarted = false
    }

    AnimatedVisibility(
        visible = !showLoading,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        Box(modifier = modifier) {
            content()
        }
    }

    AnimatedVisibility(
        visible = showLoading,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        Box(modifier = modifier) {
            loadingContent()
        }
    }
}