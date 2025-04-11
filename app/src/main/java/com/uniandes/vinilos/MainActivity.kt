package com.uniandes.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uniandes.vinilos.ui.features.home.HomeScreen
import com.uniandes.vinilos.ui.features.splash.SplashScreen
import com.uniandes.vinilos.ui.theme.VinilosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VinilosTheme {
                val navController = rememberNavController()

                NavHost(navController, startDestination = "splash_screen") {
                    composable("splash_screen") {
                        SplashScreen(navController = navController)
                    }
                    composable("home_screen") {
                        HomeScreen()
                    }
                }
            }
        }
    }
}
