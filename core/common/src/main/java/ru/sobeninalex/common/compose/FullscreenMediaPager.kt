package ru.sobeninalex.common.compose

import android.widget.Toast
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import coil3.compose.AsyncImage
import net.engawapg.lib.zoomable.ZoomState
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable
import ru.sobeninalex.resources.Black
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Black87
import ru.sobeninalex.resources.White87
import ru.sobeninalex.utils.helpers.isImgUri
import ru.sobeninalex.utils.helpers.isVideoUri
import kotlin.math.absoluteValue

private const val MIDDLE_VERTICAL_PAGE = 1

private fun isEven(value: Int) = value % 2 == 0
private fun isOdd(value: Int) = value % 2 == 1

@Composable
fun FullscreenMediaPager(
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
            .background(Black)
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
            containerColor = Black87,
            navigationIconContentColor = White87,
            titleContentColor = White87
        )
    }
}

@Composable
private fun HorizontalMediaPager(
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
    ) { page ->
        val currentMediaFile = mediaFiles[page].toString()

        when {
            currentMediaFile.isImgUri -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .onGloballyPositioned {
                            val containerSize = Size(it.size.width.toFloat(), it.size.height.toFloat())
                            evenZoomState.setLayoutSize(containerSize)
                            oddZoomState.setContentSize(containerSize)
                        }
                        .zoomable(if (isOdd(page)) oddZoomState else evenZoomState)
                ) {
                    AsyncImage(
                        model = mediaFiles[page],
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned {
                                val imageSize =
                                    Size(it.size.width.toFloat(), it.size.height.toFloat())
                                if (isOdd(page)) {
                                    oddZoomState.setContentSize(imageSize)
                                } else {
                                    evenZoomState.setContentSize(imageSize)
                                }
                            }
                    )
                }
            }

            currentMediaFile.isVideoUri -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    VideoPlayer(
                        model = mediaFiles[page],
                        isPlaying = currentPage == page
                    )
                }
            }

            else -> {
                Toast.makeText(context, "Новозможно открыть файл", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@OptIn(UnstableApi::class)
@Composable
private fun VideoPlayer(
    model: Any?,
    isPlaying: Boolean
) {
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    val context = LocalContext.current

    val mediaItem = MediaItem.fromUri(model.toString())

    val mediaSource =
        ProgressiveMediaSource.Factory(DefaultHttpDataSource.Factory())
            .createMediaSource(mediaItem)

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaSource(mediaSource)
            prepare()
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    DisposableEffect(isPlaying) {
        if (isPlaying) {
            exoPlayer.playWhenReady = true
            exoPlayer.play()
        } else {
            exoPlayer.playWhenReady = false
            exoPlayer.pause()
        }
        onDispose { }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            PlayerView(context).apply {
                player = exoPlayer
            }
        },
        update = { playerView ->
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> {
                    playerView.onResume()
                    if (isPlaying) playerView.player?.play()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    playerView.onPause()
                    playerView.player?.stop()
                }

                else -> Unit
            }
        }
    )
}
