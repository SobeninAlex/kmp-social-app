package ru.sobeninalex.common.compose

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Body_Normal16
import java.io.File

@Composable
fun AttachmentsBottomSheet(
    shouldPickFiles: Boolean = false,
    onPick: (List<Uri>) -> Unit,
    onDismissRequest: () -> Unit,
) {
    val context = LocalContext.current
    var cameraUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }
    var type = PickType.PICK_IMAGE_AND_VIDEO
    val authority = "com.example.kmp_social_app.fileprovider"

    //лаунчер фото камеры
    val cameraPictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                onPick(listOf(cameraUri))
                onDismissRequest()
            }
        }
    )

    //лаунчер видео камеры
    val cameraVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CaptureVideo(),
        onResult = { success ->
            if (success) {
                onPick(listOf(cameraUri))
                onDismissRequest()
            }
        }
    )

    //лаунчер прикрепления документов
    val pickDocumentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments(),
        onResult = { listUri ->
            onPick(listUri)
            onDismissRequest()
        }
    )

    //лаунчер прикрепления фото / видео
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { listUri ->
            onPick(listUri)
            onDismissRequest()
        }
    )

    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val notGranted = permissions.filterNot { it.value }.map { it.key }
            if (notGranted.isEmpty()) {
                when (type) {
                    PickType.CAMERA_IMAGE -> {
                        val file = File.createTempFile(
                            "picture_${System.currentTimeMillis()}",
                            ".jpg",
                            context.externalCacheDir
                        )
                        cameraUri = FileProvider.getUriForFile(context, authority, file)
                        cameraPictureLauncher.launch(cameraUri)
                    }

                    PickType.CAMERA_VIDEO -> {
                        val file = File.createTempFile(
                            "movie_${System.currentTimeMillis()}",
                            ".mp4",
                            context.externalCacheDir
                        )
                        cameraUri = FileProvider.getUriForFile(context, authority, file)
                        cameraVideoLauncher.launch(cameraUri)
                    }

                    PickType.PICK_FILES -> {
                        pickDocumentLauncher.launch(
                            arrayOf(
                                "text/html",
                                "text/plain",
                                "application/xml",
                                "application/pdf",
                                "audio/mpeg",
                                "text/csv",
                                "application/dbase",
                                "application/dbf",
                                "application/zip",
                                "application/rar",
                                "application/x-tar",
                                "application/msword",
                                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                                "application/vnd.ms-excel",
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                                "application/vnd.oasis.opendocument.text",
                                "application/vnd.oasis.opendocument.graphics",
                                "image/vnd.microsoft.icon",
                                "application/vnd.rar",
                                "application/x-rar-compressed",
                            )
                        )
                    }

                    PickType.PICK_IMAGE_AND_VIDEO -> {
                        pickMediaLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
                        )
                    }
                }
            }
        }
    )

    TitleBottomSheet(
        title = stringResource(R.string.attach),
        onDismissRequest = onDismissRequest
    ) {
        Column {
            PickerButton(
                painter = painterResource(id = R.drawable.ic_camera_fill_24_black),
                text = stringResource(id = R.string.take_photo),
                onClick = {
                    type = PickType.CAMERA_IMAGE
                    activityResultLauncher.launch(cameraPermissions)
                }
            )
            HorizontalLine(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            PickerButton(
                painter = painterResource(id = R.drawable.ic_videocamera_fill_24_black),
                text = stringResource(id = R.string.take_video),
                onClick = {
                    type = PickType.CAMERA_VIDEO
                    activityResultLauncher.launch(videoCameraPermissions)
                }
            )
            HorizontalLine(
                modifier = Modifier.padding(vertical = 16.dp)
            )

            PickerButton(
                painter = painterResource(id = R.drawable.ic_image_fill_24_black),
                text = stringResource(id = R.string.pick_image),
                onClick = {
                    type = PickType.PICK_IMAGE_AND_VIDEO
                    activityResultLauncher.launch(galleryPermissions)
                }
            )

            if (shouldPickFiles) {
                HorizontalLine(
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                PickerButton(
                    painter = painterResource(id = R.drawable.ic_folder_fill_24_black),
                    text = stringResource(id = R.string.local_storage),
                    onClick = {
                        type = PickType.PICK_FILES
                        activityResultLauncher.launch(documentsPermissions)
                    }
                )
            }
        }
    }
}

@Composable
private fun PickerButton(
    painter: Painter,
    text: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Icon(
            painter = painter,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = Body_Normal16,
            modifier = Modifier.weight(1f)
        )
    }
}

// Разрешения необходимые для работы камеры
private val cameraPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.CAMERA,
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

// Разрешения необходимые для работы видео камеры
private val videoCameraPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO
        )
    } else {
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

// Разрешения необходимые для выбора изображений и видео в галерее
private val galleryPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
        )
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

// Разрешения необходимые для выбора файлов
private val documentsPermissions: Array<String>
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
        )
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

private fun String.isVideo(): Boolean {
    return contains(".*\\.(mp4|webm)".toRegex(RegexOption.IGNORE_CASE))
}

private enum class PickType {
    CAMERA_IMAGE, CAMERA_VIDEO, PICK_FILES, PICK_IMAGE_AND_VIDEO
}