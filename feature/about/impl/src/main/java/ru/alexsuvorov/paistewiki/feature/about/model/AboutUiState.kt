package ru.alexsuvorov.paistewiki.feature.about.model

data class AboutUiState(
    val isNotificationsEnabled: Boolean = false,
    val currentLanguage: String = ""
)