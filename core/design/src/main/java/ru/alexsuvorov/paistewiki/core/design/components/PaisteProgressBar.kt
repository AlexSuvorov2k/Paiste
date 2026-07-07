package ru.alexsuvorov.paistewiki.core.design.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PaisteProgressBar(
    modifier: Modifier = Modifier,
    color: Color = ProgressIndicatorDefaults.circularColor
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = color
    )
}
