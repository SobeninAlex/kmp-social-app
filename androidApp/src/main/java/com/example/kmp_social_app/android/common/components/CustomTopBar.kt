package com.example.kmp_social_app.android.common.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.kmp_social_app.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    onBackClicked: (() -> Unit)? = null,
    elevation: Dp = 1.dp,
    containerColor: Color = MaterialTheme.colorScheme.secondary,
    navigationIconContentColor: Color = MaterialTheme.colorScheme.onBackground,
    titleContentColor: Color = MaterialTheme.colorScheme.onBackground,
    actionIconContentColor: Color = MaterialTheme.colorScheme.primary,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            navigationIconContentColor = navigationIconContentColor,
            titleContentColor = titleContentColor,
            actionIconContentColor = actionIconContentColor,
        ),
        navigationIcon = {
            onBackClicked?.let {
                IconButton(
                    onClick = onBackClicked
                ) {
                    Icon(
                        painter = painterResource(R.drawable.round_arrow_back),
                        contentDescription = null,
                    )
                }
            }
        },
        actions = actions,
        modifier = Modifier.shadow(elevation = elevation)
    )
}