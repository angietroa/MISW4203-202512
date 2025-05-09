package com.uniandes.vinilos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.uniandes.vinilos.R

@Composable
fun LogoHeader(
    route: String,
    navController: NavHostController,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            IconButton(
                onClick = { if (route.isNotBlank()) {
                    navController.navigate(route) {
                        popUpTo(route) { inclusive = true }
                    }
                } else {
                    navController.popBackStack()
                } },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .size(24.dp).testTag("navigation_back")
                )
            }

            Image(
                painter = painterResource(id = R.drawable.long_logo),
                contentDescription = "Logo de la app",
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(48.dp)
            )
        }
    }
}

