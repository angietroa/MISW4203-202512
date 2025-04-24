package com.uniandes.vinilos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.uniandes.vinilos.ui.features.album.*
import com.uniandes.vinilos.ui.features.artist.*
import com.uniandes.vinilos.ui.features.collector.*
import com.uniandes.vinilos.ui.features.home.HomeScreen
import com.uniandes.vinilos.ui.features.splash.SplashScreen
import com.uniandes.vinilos.ui.theme.VinilosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VinilosTheme {
                val navController = rememberNavController()

                val startDestination = if (BuildConfig.SKIP_SPLASH_FOR_UI_TEST) {
                    "home_screen"
                } else {
                    "splash_screen"
                }

                NavHost(navController = navController, startDestination = startDestination) {
                    composable("splash_screen") {
                        SplashScreen(navController = navController)
                    }

                    composable("home_screen") {
                        HomeScreen(navController)
                    }

                    composable("album_screen") {
                        AlbumScreen(navController)
                    }

                    composable(
                        route = "album_detail/{albumId}?origin={origin}",
                        arguments = listOf(
                            navArgument("albumId") { type = NavType.StringType },
                            navArgument("origin") {
                                type = NavType.StringType
                                defaultValue = null
                                nullable = true
                            }
                        )
                    ) { backStackEntry ->
                        val albumId = backStackEntry.arguments?.getString("albumId") ?: ""
                        val origin  = backStackEntry.arguments?.getString("origin") ?: ""
                        AlbumDetail(albumId = albumId, origin = origin, navController = navController)
                    }

                    composable("album_create") {
                        AlbumCreate(navController)
                    }

                    composable("artist_screen") {
                        ArtistScreen(navController)
                    }

                    composable(
                        route = "artist_detail/{artistId}?origin={origin}",
                        arguments = listOf(
                            navArgument("artistId") { type = NavType.StringType },
                            navArgument("origin") {
                                type = NavType.StringType
                                defaultValue = null
                                nullable = true
                            }
                        )
                    ) { backStackEntry ->
                        val artistId = backStackEntry.arguments?.getString("artistId") ?: ""
                        val origin   = backStackEntry.arguments?.getString("origin") ?: ""
                        ArtistDetail(artistId = artistId, origin = origin, navController = navController)
                    }

                    composable("artist_create") {
                        ArtistCreate(navController)
                    }

                    composable("collector_screen") {
                        CollectorScreen(navController)
                    }

                    composable("collector_add/{collectorId}") { backStackEntry ->
                        val collectorId = backStackEntry.arguments?.getString("collectorId") ?: ""
                        CollectorAdd(collectorId = collectorId, navController = navController)
                    }
                }
            }
        }
    }
}
