package ru.sobeninalex.common.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.sobeninalex.resources.Title_Bold18

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    title: String,
    visibility: Boolean
) {
    Animate(
        visible = visibility,
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = Title_Bold18,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}