package ru.alexsuvorov.paistewiki.core.design.components

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PaisteDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = 1.dp,
    color: Color = Color.LightGray
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color
    )
}
