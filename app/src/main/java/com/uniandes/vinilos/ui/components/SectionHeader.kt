package com.uniandes.vinilos.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.navigation.NavHostController

@Composable
fun SectionHeader(
    title: String,
    route: String,
    navController: NavHostController,
    tag: String,
    isList: Boolean = false,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.primary
        )

        val (buttonText, buttonColors) = getButtonConfig(isList)

        Button(
            onClick = { navController.navigate(route) },
            modifier = Modifier.testTag(tag),
            colors = buttonColors,
            border = BorderStroke(1.dp, buttonColors.containerColor),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp)
        ) {
            Text(text = buttonText)
        }
    }
    Spacer(modifier = Modifier.height(18.dp))
}

@Composable
fun getButtonConfig(isList: Boolean): Pair<String, ButtonColors> {
    val buttonText = if (isList) "Crear" else "Ver más"
    val containerColor = if (isList) Color(0xFF059BFF).copy(alpha = 0.4f) else Color.Transparent
    val contentColor = if (isList) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary

    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor
    )

    return buttonText to buttonColors
}
