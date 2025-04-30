package com.uniandes.vinilos.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun FormButtons (
    routeBack: String,
    navController: NavHostController,
    isAdd: Boolean = false,
    onClickCreate: () -> Unit,
) {
    val buttonText = if (isAdd) "Agregar" else "Crear"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { navController.navigate(routeBack) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA1A1A1).copy(alpha = 0.4f),
                    contentColor = Color(0xFFA1A1A1)
                ),
                border = BorderStroke(1.dp, Color(0xFFA1A1A1)),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = "Cancelar")
            }
            Button(
                onClick = onClickCreate,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF059BFF).copy(alpha = 0.4f),
                    contentColor = MaterialTheme.colorScheme.tertiary
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(text = buttonText)
            }
        }
    }

}