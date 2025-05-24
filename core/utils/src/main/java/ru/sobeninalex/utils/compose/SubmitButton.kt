package ru.sobeninalex.utils.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import ru.sobeninalex.resources.DarkGray
import ru.sobeninalex.resources.White

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    disabledContainerColor: Color = ru.sobeninalex.resources.DarkGray,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    contentColor: Color = ru.sobeninalex.resources.White,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        shape = shape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor,
            contentColor = contentColor
        ),
        contentPadding = contentPadding,
        modifier = modifier,
    ) {
        content()
    }
}