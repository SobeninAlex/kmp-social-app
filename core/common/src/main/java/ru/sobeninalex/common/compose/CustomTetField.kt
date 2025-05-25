package ru.sobeninalex.common.compose

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.sobeninalex.resources.R

@Composable
fun CustomTetField(
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPasswordTextField: Boolean = false,
    onSendMessage: (() -> Unit)? = null,
    isLoading: Boolean = false,
    isSingleLine: Boolean = true,
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes placeholder: Int
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = MaterialTheme.typography.bodySmall,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),
        singleLine = isSingleLine,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
        ),
        trailingIcon = {
            if (isPasswordTextField) {
                PasswordEyeIcon(
                    isPasswordVisible = isPasswordVisible,
                    onPasswordVisibilityToggle = { isPasswordVisible = !isPasswordVisible }
                )
            }

            onSendMessage?.let { send ->
                val enabled = value.isNotBlank()

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                } else {
                    IconButton(
                        enabled = enabled,
                        onClick = send
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_send_24),
                            contentDescription = null,
                            tint = if (enabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        },
        visualTransformation = if (isPasswordTextField) {
            if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        } else VisualTransformation.None,
        placeholder = {
            Text(
                text = stringResource(id = placeholder),
                style = MaterialTheme.typography.bodySmall,
                color = ru.sobeninalex.resources.LightGray
            )
        },
        shape = MaterialTheme.shapes.large,
    )
}

@Composable
private fun PasswordEyeIcon(
    isPasswordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit
) {
    val painter = if (isPasswordVisible) {
        painterResource(R.drawable.show_eye_icon_filled)
    } else {
        painterResource(R.drawable.hide_eye_icon_filled)
    }

    IconButton(
        onClick = onPasswordVisibilityToggle
    ) {
        Icon(
            painter = painter,
            contentDescription = null
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
private fun CustomTetFieldPreviewDark() {
    ru.sobeninalex.resources.MainAppTheme {
        CustomTetField(
            value = "sdfsf",
            onValueChange = {},
            placeholder = R.string.signup_button,
            onSendMessage = {},
            isLoading = false
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
private fun CustomTetFieldPreview() {
    ru.sobeninalex.resources.MainAppTheme {
        CustomTetField(
            value = "sdfsf",
            onValueChange = {},
            placeholder = R.string.signup_button,
            onSendMessage = {},
            isLoading = false
        )
    }
}