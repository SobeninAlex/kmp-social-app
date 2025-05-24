package ru.sobeninalex.common.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.sobeninalex.common.event.SnackbarEvent
import ru.sobeninalex.common.event.UnauthorizedEvent
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.SomethingWrongException
import ru.sobeninalex.utils.helpers.UnauthorizedException
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import java.io.IOException

open class BaseViewModel : ViewModel(), KoinComponent {

    private val userPrefs by inject<UserPreferences>()

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

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

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
            userPrefs.clearUserSetting()
            UnauthorizedEvent.sendEvent()
        }
    }
}