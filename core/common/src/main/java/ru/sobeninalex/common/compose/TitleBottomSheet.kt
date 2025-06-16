package ru.sobeninalex.common.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.sobeninalex.resources.Body_Normal14
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Title_Bold16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBottomSheet(
    onDismissRequest: () -> Unit,
    title: String,
    subTitle: String? = null,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    footer: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val skipPartiallyExpanded = footer != null
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

    ModalBottomSheet(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = containerColor,
        shape = shape,
        dragHandle = { CustomDragHandle() },
    ) {
        BottomSheetTitle(
            title = title,
            subTitle = subTitle,
            onCloseClick = {
                scope.launch {
                    sheetState.hide()
                    onDismissRequest()
                }
            }
        )

        HorizontalLine()

        Box(modifier = Modifier.weight(1f)) {
            content()
        }

        footer?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(containerColor)
            ) {
                HorizontalLine()
                it()
            }
        }
    }
}

@Composable
private fun BottomSheetTitle(
    title: String,
    subTitle: String? = null,
    onCloseClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = Title_Bold16,
                color = MaterialTheme.colorScheme.onBackground
            )
            subTitle?.let {
                Text(
                    text = it,
                    style = Body_Normal14,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Icon(
            painter = painterResource(id = R.drawable.close_circle_fill_24),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.clickable { onCloseClick() }
        )
    }
}