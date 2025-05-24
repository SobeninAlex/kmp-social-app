package ru.sobeninalex.common.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import ru.sobeninalex.resources.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBottomSheet(
    text: String,
    isLoading: Boolean,
    onSendClick: (String) -> Unit,
    requestFocus: Boolean = true,
    onDismissRequest: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
) {
    var value by remember { mutableStateOf(text) }

    val focusRequester by remember {
        derivedStateOf {
            FocusRequester()
        }
    }

    LaunchedEffect(requestFocus) {
        if (requestFocus) {
            delay(200)
            focusRequester.requestFocus()
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        containerColor = containerColor,
        shape = RectangleShape,
        dragHandle = null
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomTetField(
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                isSingleLine = false,
                value = value,
                onSendMessage = { onSendClick(value) },
                isLoading = isLoading,
                onValueChange = { value = it },
                placeholder = R.string.your_comment_hint
            )
        }
    }
}