package ru.sobeninalex.common.compose

import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.sobeninalex.resources.Buttons_Medium14
import ru.sobeninalex.resources.DarkGray
import ru.sobeninalex.resources.R
import ru.sobeninalex.resources.Title_Bold18

@Composable
fun RepeatScreen(
    modifier: Modifier = Modifier,
    visibility: Boolean = true,
    onRepeatClick: () -> Unit,
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visibility,
        enter = fadeIn(tween(500)),
        exit = fadeOut(tween(500))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.something_went_wrong),
                color = DarkGray,
                style = Title_Bold18
            )

            Spacer(modifier = Modifier.height(8.dp))

            SubmitButton(
                onClick = onRepeatClick
            ) {
                Text(
                    text = stringResource(R.string.repeat),
                    style = Buttons_Medium14
                )
            }
        }
    }
}