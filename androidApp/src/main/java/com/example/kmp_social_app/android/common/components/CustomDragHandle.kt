package com.example.kmp_social_app.android.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomDragHandle(
    modifier: Modifier = Modifier,
    width: Dp = 32.dp,
    height: Dp = 4.dp,
    shape: Shape = RoundedCornerShape(4.dp),
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Surface(
        modifier = modifier.padding(top = 8.dp),
        color = color,
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .size(width = width, height = height)
        )
    }
}