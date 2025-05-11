package com.uniandes.vinilos.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.uniandes.vinilos.R
import com.uniandes.vinilos.ui.components.MainAlbum
import com.uniandes.vinilos.ui.components.MainArtist
import com.uniandes.vinilos.ui.components.SectionHeader
import com.uniandes.vinilos.viewmodel.AlbumViewModel
import com.uniandes.vinilos.viewmodel.ArtistViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    albumViewModel: AlbumViewModel = viewModel(),
    artistViewModel: ArtistViewModel = viewModel()
) {
    val albumState by albumViewModel.albumState.collectAsState()
    val artistState by artistViewModel.artistState.collectAsState()

    LaunchedEffect(key1 = true) {
        albumViewModel.loadAlbums()
        artistViewModel.loadArtists()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .testTag("home_screen")
    ) {
        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.long_logo),
                contentDescription = "Logo de la app",
                modifier = Modifier.height(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
        SectionHeader(title = "Álbumes", route = "album_screen", navController = navController, tag = "show_more_album")

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when {
                albumState.isLoading -> {
                    CircularProgressIndicator()
                }
                albumState.error != null -> {
                    Text(
                        text = "Error: ${albumState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                albumState.albums.isNotEmpty() -> {
                    LazyRow(
                        modifier = Modifier.testTag("album_list"),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(albumState.albums.take(3)) { album ->
                            MainAlbum(
                                modifier = Modifier.testTag("album_item"),
                                cover = album.cover,
                                title = album.name,
                                subtitle = album.performers.joinToString(", ") { it.name },
                                onClick = {
                                    navController.navigate("album_detail/${album.id}?origin=home_screen")
                                }
                            )
                        }
                    }
                }
                else -> {
                    Text(text = "No hay álbumes disponibles")
                }
            }
        }
        Spacer(modifier = Modifier.height(36.dp))
        SectionHeader(title = "Artistas", route = "artist_screen", navController = navController, tag = "show_more_artist")

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            when {
                artistState.isLoading -> {
                    CircularProgressIndicator()
                }
                artistState.error != null -> {
                    Text(
                        text = "Error: ${artistState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                artistState.artists.isNotEmpty() -> {
                    LazyRow(
                        modifier = Modifier.testTag("artist_list"),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(artistState.artists.take(3)) { artist ->
                            MainArtist(
                                modifier = Modifier.testTag("artist_item"),
                                cover = artist.image,
                                title = artist.name,
                                onClick = {
                                    navController.navigate("artist_detail/${artist.id}?origin=home_screen")
                                }
                            )
                        }
                    }
                }
                else -> {
                    Text(text = "No hay artistas disponibles")
                }
            }
        }
        Spacer(modifier = Modifier.height(36.dp))
        SectionHeader(title = "Coleccionistas", route = "collector_screen", navController = navController, tag = "show_more_collector")
    }
}