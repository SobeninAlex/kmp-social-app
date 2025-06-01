package ru.sobeninalex.common.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import ru.sobeninalex.resources.Black54
import ru.sobeninalex.resources.DarkGray
import ru.sobeninalex.resources.White
import ru.sobeninalex.resources.roundedCornerShape8

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = roundedCornerShape8,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    disabledContainerColor: Color = DarkGray,
    contentColor: Color = White,
    disabledContentColor: Color = Black54,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor
        ),
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        content()
    }
}