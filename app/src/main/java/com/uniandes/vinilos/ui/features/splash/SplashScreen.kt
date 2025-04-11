package com.uniandes.vinilos.ui.features.splash

import android.os.Handler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.uniandes.vinilos.R
import com.uniandes.vinilos.ui.theme.VinilosTheme

@Composable
fun SplashScreen(navController: NavController) {
    VinilosTheme{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo de Vinilos",
                modifier = Modifier.size(200.dp)
            )
        }

        Handler().postDelayed({
            navController.navigate("home_screen")
        }, 3000)
    }
}
