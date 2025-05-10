package com.example.kmp_social_app.android.common.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    protected fun showSnackbar(
        message: String?,
        action: SnackbarAction? = null
    ) {
        if (message.isNullOrEmpty()) return

        viewModelScope.launch {
            SnackbarController.sendEvent(
                event = SnackbarEvent(
                    message = message,
                    snackbarAction = action
                )
            )
        }
    }

    protected val resources = Core.resources
}