package ru.alexsuvorov.paistewiki.feature.about

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.alexsuvorov.paistewiki.feature.about.model.AboutIntent
import ru.alexsuvorov.paistewiki.feature.about.model.AboutUiState
import ru.alexsuvorov.paistewiki.feature.about.reducer.AboutViewReducer
import ru.alexsuvorov.paistewiki.tools.AppPreferences

internal class AboutViewModel(
    private val appPreferences: AppPreferences,
    private val reducer: AboutViewReducer = AboutViewReducer()
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        AboutUiState(
            isNotificationsEnabled = appPreferences.getText("enable_notifications") == "1",
            currentLanguage = appPreferences.getText("choosed_lang")
        )
    )
    val uiState: StateFlow<AboutUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: AboutIntent) {
        // Handle side effects (Persistence)
        when (intent) {
            is AboutIntent.ToggleNotifications -> {
                val newState = !_uiState.value.isNotificationsEnabled
                appPreferences.saveText("enable_notifications", if (newState) "1" else "0")
            }
            is AboutIntent.ChangeLanguage -> {
                appPreferences.saveText("choosed_lang", intent.lang)
            }
        }

        // Apply state transition through Reducer
        _uiState.update { currentState ->
            reducer.reduce(currentState, intent)
        }
    }

    // Helper for legacy service logic if needed
    fun isNotificationsActive(): Boolean = _uiState.value.isNotificationsEnabled
}
