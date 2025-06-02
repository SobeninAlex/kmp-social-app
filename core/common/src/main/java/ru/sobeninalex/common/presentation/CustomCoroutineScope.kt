package ru.sobeninalex.common.presentation

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.sobeninalex.common.event.other.SnackbarEvent
import ru.sobeninalex.common.event.other.UnauthorizedEvent
import ru.sobeninalex.utils.helpers.Constants
import ru.sobeninalex.utils.helpers.SomethingWrongException
import ru.sobeninalex.utils.helpers.UnauthorizedException
import ru.sobeninalex.utils.preferences.user_prefs.UserPreferences
import java.io.IOException
import kotlin.coroutines.CoroutineContext

object CustomCoroutineScope: CoroutineScope, KoinComponent {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main.immediate + SupervisorJob() + exceptionHandler

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

    private fun showSnackbar(
        message: String?,
        action: SnackbarEvent.Action? = null
    ) {
        if (message.isNullOrEmpty()) return

        launch {
            SnackbarEvent.sendEvent(
                event = SnackbarEvent.Event(
                    message = message,
                    action = action
                )
            )
        }
    }

    private fun sendUnauthorizedEvent() {
        launch {
            userPrefs.clearUserSetting()
            UnauthorizedEvent.sendEvent()
        }
    }
}