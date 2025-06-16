package ru.sobeninalex.common.compose

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import net.engawapg.lib.zoomable.ZoomState
import net.engawapg.lib.zoomable.rememberZoomState
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Black24
import ru.sobeninalex.resources.Black54
import ru.sobeninalex.resources.White87
import ru.sobeninalex.utils.helpers.isImgUri
import kotlin.math.absoluteValue

private const val MIDDLE_VERTICAL_PAGE = 1

private fun isEven(value: Int) = value % 2 == 0
private fun isOdd(value: Int) = value % 2 == 1

@Composable
fun MediaPager(
    mediaFiles: List<Any>,
    startMediaFile: Any,
    onDismissRequest: () -> Unit,
) {
    var verticalScrollEnabled by remember { mutableStateOf(true) }

    /*вертикальный свайп приводит к закрытию диалога*/
    val verticalPagerState = rememberPagerState(
        initialPage = MIDDLE_VERTICAL_PAGE,
        pageCount = { 3 }
    )

    /*горизонтальный свайп переключает картинки*/
    val horizontalPagerState = rememberPagerState(
        initialPage = mediaFiles.indexOf(startMediaFile)/*.coerceAtLeast(0)*/,
        pageCount = { mediaFiles.size }
    )

    val oddZoomState = rememberZoomState()
    val evenZoomState = rememberZoomState()

    /*при горизонтальном листании сбрасываем настройки масштабирования у той картинки, которая сейчас не видна на экране*/
    LaunchedEffect(horizontalPagerState.settledPage) {
        if (isOdd(horizontalPagerState.settledPage)) {
            evenZoomState.reset()
        } else {
            oddZoomState.reset()
        }
    }

    /*закрываем диалог при свайпе вверх/вниз, если изменилась вертикальная страничка*/
    LaunchedEffect(verticalPagerState.currentPage) {
        if (verticalPagerState.currentPage == MIDDLE_VERTICAL_PAGE) return@LaunchedEffect
        onDismissRequest()
    }

    /*если начали вертикальный скролл, а потом поставили на экран второй палец, то по умолчанию,
    pager заканчивает скролл, что приводит к закрытию диалога, нам же наоборот надо вернуться к просмотру картинки*/
    LaunchedEffect(verticalScrollEnabled) {
        verticalPagerState.scrollToPage(MIDDLE_VERTICAL_PAGE)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                /*в зависимости от текущего вертикального смещения управляем прозрачнотью фона*/
                val verticalOffset = (verticalPagerState.currentPage - MIDDLE_VERTICAL_PAGE) +
                        verticalPagerState.currentPageOffsetFraction
                alpha = lerp(
                    start = 1f,
                    stop = 0f,
                    fraction = verticalOffset.absoluteValue.coerceIn(0f..1f)
                )
            }
            .background(Black24)
            .pointerInput(Unit) {
                awaitEachGesture {
                    do {
                        /*при зуммировании изображения (pinch gesture) возникали ложные срабатывания вертикального пейджера,
                        который закрывает данный диалог. Чтобы избежать ложных срабатываний, буду запрещать вертикальный
                        скролл если с экраном взаимодействует более одного пальца*/
                        val event = awaitPointerEvent()
                        val fingersOnScreen = event.changes.size
                        verticalScrollEnabled = fingersOnScreen < 2
                    } while (event.changes.any { it.pressed })
                }
            }
    ) {
        VerticalPager(
            state = verticalPagerState,
            userScrollEnabled = verticalScrollEnabled
        ) {
            if (it == MIDDLE_VERTICAL_PAGE) {
                HorizontalMediaPager(
                    mediaFiles = mediaFiles,
                    pagerState = horizontalPagerState,
                    evenZoomState = evenZoomState,
                    oddZoomState = oddZoomState
                )
            } else {
                Box(modifier = Modifier.fillMaxSize())
            }
        }

        CustomTopBar(
            title = stringResource(
                id = R.string.image_pager_title,
                horizontalPagerState.currentPage + 1,
                mediaFiles.size
            ),
            onBackClick = onDismissRequest,
            containerColor = Black54,
            navigationIconContentColor = White87,
            titleContentColor = White87
        )
    }
}

@Composable
fun HorizontalMediaPager(
    mediaFiles: List<Any>,
    pagerState: PagerState,
    evenZoomState: ZoomState,
    oddZoomState: ZoomState
) {
    val currentPage = pagerState.currentPage
    val context = LocalContext.current

    HorizontalPager(
        state = pagerState,
        beyondViewportPageCount = 1,
        pageSpacing = 12.dp
    ) {page ->
        val currentMediaFile = mediaFiles[page].toString()

        when {
            currentMediaFile.isImgUri() -> {}

            else -> {
                Toast.makeText(context, "Новозможно открыть файл", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
