package ru.alexsuvorov.paistewiki.feature.about.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ru.alexsuvorov.paistewiki.feature.about.AboutViewModel
import ru.alexsuvorov.paistewiki.feature.about.model.AboutIntent

@Composable
internal fun AboutScreen(
    viewModel: AboutViewModel,
    onLanguageClick: () -> Unit,
    onHelpClick: () -> Unit,
    onVkClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    AboutContent(
        state = state,
        onToggleNotifications = { viewModel.handleIntent(AboutIntent.ToggleNotifications) },
        onLanguageClick = onLanguageClick,
        onHelpClick = onHelpClick,
        onVkClick = onVkClick
    )
}