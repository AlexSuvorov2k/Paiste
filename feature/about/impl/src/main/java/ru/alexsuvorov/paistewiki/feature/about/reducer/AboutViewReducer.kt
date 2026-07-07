package ru.alexsuvorov.paistewiki.feature.about.reducer

import ru.alexsuvorov.paistewiki.feature.about.model.AboutIntent
import ru.alexsuvorov.paistewiki.feature.about.model.AboutUiState

internal class AboutViewReducer {

    fun reduce(state: AboutUiState, intent: AboutIntent): AboutUiState {
        return when (intent) {
            is AboutIntent.ToggleNotifications -> state.copy(
                isNotificationsEnabled = state.isNotificationsEnabled.not()
            )

            is AboutIntent.ChangeLanguage -> state.copy(
                currentLanguage = intent.lang
            )
        }
    }
}