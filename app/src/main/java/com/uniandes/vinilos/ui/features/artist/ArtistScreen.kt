package com.uniandes.vinilos.ui.features.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.components.SectionHeader
import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.ui.components.SecondaryArtist
import com.uniandes.vinilos.viewmodel.ArtistViewModel

@Composable
fun ArtistScreen(navController: NavHostController, artistViewModel: ArtistViewModel = viewModel()) {

    val artistState by artistViewModel.artistState.collectAsState()

    LaunchedEffect(Unit) {
        artistViewModel.loadArtists()
    }

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
        Spacer(modifier = Modifier.height(18.dp))

        if (artistState.isLoading) {
            Text("Cargando artistas...", modifier = Modifier.testTag("loading_artists"))
        } else if (artistState.error != null) {
            Text("Error: ${artistState.error}", modifier = Modifier.testTag("error_artists"))
        } else {
            LazyColumn(
                modifier = Modifier.testTag("artist_list"),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(artistState.artists) { artist ->
                    SecondaryArtist(
                        modifier = Modifier.testTag("artist_item"),
                        title = artist.name,
                        cover = artist.image,
                        onClick = {
                            navController.navigate("artist_detail/${artist.id}")
                        }
                    )
                }
            }
        }
    }
}
