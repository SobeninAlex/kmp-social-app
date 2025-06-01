package ru.sobeninalex.common.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.sobeninalex.common.event.SnackbarEvent

open class BaseViewModel : ViewModel(), KoinComponent {

    protected val viewModelScope by inject<CustomCoroutineScope>()

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
}