package ru.sobeninalex.common.compose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullRefreshLayout(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    pullToRefreshState: PullToRefreshState = rememberPullToRefreshState(),
    content: @Composable BoxScope.() -> Unit
) {
    val indicatorColors: List<Color> = listOf(
        Color(0xFF0B26E0),
        Color(0xFFFF1200),
        Color(0xFF14B91E),
        Color(0xFFFFBF00)
    )

    val rotationDuration = 1332L

    var currentColorIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(isRefreshing, indicatorColors.size) {
        if (isRefreshing) {
            currentColorIndex = 0
            while (isActive) {
                delay(rotationDuration)
                currentColorIndex = (currentColorIndex + 1) % indicatorColors.size
            }
        } else {
            currentColorIndex = 0
        }
    }

    val currentIndicatorColor = if (isRefreshing) {
        indicatorColors[currentColorIndex]
    } else {
        Color(0xFF0B26E0)
    }

    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        state = pullToRefreshState,
        indicator = {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = isRefreshing,
                state = pullToRefreshState,
                containerColor = MaterialTheme.colorScheme.secondary,
                color = currentIndicatorColor
            )
        }
    ) {
        content()
    }
}