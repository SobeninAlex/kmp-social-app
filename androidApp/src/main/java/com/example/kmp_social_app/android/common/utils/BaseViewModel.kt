package com.example.kmp_social_app.android.common.utils

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.example.kmp_social_app.android.common.utils.event.SnackbarEvent
import com.example.kmp_social_app.android.common.utils.event.UnauthorizedEvent
import com.example.kmp_social_app.common.data.local.UserSettings
import com.example.kmp_social_app.common.utils.Constants
import com.example.kmp_social_app.common.utils.SomethingWrongException
import com.example.kmp_social_app.common.utils.UnauthorizedException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.io.IOException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class BaseViewModel : ViewModel(), KoinComponent {

    private val dataStore by inject<DataStore<UserSettings>>()

    protected val resources = Core.resources

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is UnauthorizedException -> {
                showSnackbar(message = throwable.message)
                sendUnauthorizedEvent()
            }
            is SomethingWrongException -> showSnackbar(message = throwable.message)
            is IOException -> showSnackbar(message = Constants.NO_INTERNET_ERROR)
            else -> showSnackbar(message = throwable.message)
        }
    }

    protected val viewModelScope = CoroutineScope(
        Dispatchers.Main.immediate + SupervisorJob() + exceptionHandler
    )

    protected fun showSnackbar(
        message: String?,
        action: SnackbarEvent.Action? = null
    ) {
        if (message.isNullOrEmpty()) return

        viewModelScope.launch {
            SnackbarEvent.sendEvent(
                event = SnackbarEvent.Event(
                    message = message,
                    action = action
                )
            )
        }
    }

    private fun sendUnauthorizedEvent() {
        viewModelScope.launch {
            dataStore.updateData { UserSettings() }
            UnauthorizedEvent.sendEvent()
        }
    }
}