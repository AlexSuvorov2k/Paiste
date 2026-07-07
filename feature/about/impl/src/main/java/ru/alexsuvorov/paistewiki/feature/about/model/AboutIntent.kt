package ru.alexsuvorov.paistewiki.feature.about.model

internal sealed interface AboutIntent {
    object ToggleNotifications : AboutIntent
    data class ChangeLanguage(val lang: String) : AboutIntent
}