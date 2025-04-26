package com.uniandes.vinilos.ui.features.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.components.SectionHeader

@Composable
fun ArtistScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        LogoHeader("home_screen", navController = navController)
        Spacer(modifier = Modifier.height(32.dp))
        SectionHeader(
            title = "Artistas",
            route = "artist_create",
            navController = navController,
            tag = "create_artist",
            isList = true
        )
    }
}

