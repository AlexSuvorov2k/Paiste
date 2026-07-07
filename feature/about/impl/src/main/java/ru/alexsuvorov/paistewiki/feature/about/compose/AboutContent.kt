package ru.alexsuvorov.paistewiki.feature.about.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexsuvorov.paistewiki.core.design.components.PaisteDivider
import ru.alexsuvorov.paistewiki.core.design.components.PaisteImage
import ru.alexsuvorov.paistewiki.core.design.components.PaisteSwitch
import ru.alexsuvorov.paistewiki.core.design.components.PaisteText
import ru.alexsuvorov.paistewiki.core.design.theme.PaisteTheme
import ru.alexsuvorov.paistewiki.core.support.AppParams
import ru.alexsuvorov.paistewiki.feature.about.R
import ru.alexsuvorov.paistewiki.feature.about.model.AboutUiState

@Composable
internal fun AboutContent(
    state: AboutUiState,
    onToggleNotifications: () -> Unit,
    onLanguageClick: () -> Unit,
    onHelpClick: () -> Unit,
    onVkClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    ) {
        PaisteDivider()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onLanguageClick() }
                .padding(vertical = 8.dp, horizontal = 8.dp)
        ) {
            PaisteText(
                text = stringResource(id = R.string.choose_lang),
                color = Color(0xFFE74C3C),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            val langLabel = getLangLabelText(state.currentLanguage)
            PaisteText(
                text = langLabel,
                fontSize = 16.sp
            )
        }

        PaisteDivider(modifier = Modifier.padding(top = 6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onToggleNotifications() }
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PaisteText(
                text = stringResource(id = R.string.enable_notifications),
                color = Color(0xFFE74C3C),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            PaisteSwitch(
                checked = state.isNotificationsEnabled,
                onCheckedChange = { onToggleNotifications() }
            )
        }

        PaisteDivider()

        PaisteText(
            text = stringResource(id = R.string.help_lang),
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onHelpClick() }
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onVkClick() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PaisteImage(
                painter = painterResource(id = R.drawable.vk_logo),
                contentDescription = null,
                modifier = Modifier.size(42.dp)
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                PaisteText(
                    text = stringResource(id = R.string.vktext),
                    fontSize = 16.sp
                )
                PaisteText(
                    text = stringResource(id = R.string.vklink),
                    color = Color.Black,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun getLangLabelText(currentLang: String): String {
    val index = AppParams.LANG.indexOf(currentLang)
    return if (index != -1) {
        when(currentLang) {
            "en" -> "English"
            "ru" -> "Русский (AlexS)"
            else -> currentLang
        }
    } else {
        ""
    }
}

@Preview(showBackground = true)
@Composable
private fun AboutContentPreview() {
    PaisteTheme {
        AboutContent(
            state = AboutUiState(
                isNotificationsEnabled = true,
                currentLanguage = "ru"
            ),
            onToggleNotifications = {},
            onLanguageClick = {},
            onHelpClick = {},
            onVkClick = {}
        )
    }
}