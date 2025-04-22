package com.uniandes.vinilos.ui.features.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.components.SecondaryAlbum
import com.uniandes.vinilos.ui.components.SectionHeader

@Composable
fun AlbumScreen(navController: NavHostController) {
    val albums = listOf(
        Album(
            id = "1",
            cover = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg",
            name = "Un album",
            artist = "Andres Cepeda"
        ),
        Album(
            id = "2",
            cover = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg",
            name = "Otro album",
            artist = "Otro artista"
        ),
        Album(
            id = "2",
            cover = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg",
            name = "Otro album",
            artist = "Otro artista"
        ),
        Album(
            id = "2",
            cover = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg",
            name = "Otro album",
            artist = "Otro artista"
        ),
        Album(
            id = "2",
            cover = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg",
            name = "Otro album",
            artist = "Otro artista"
        ),
        Album(
            id = "2",
            cover = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg",
            name = "Otro album",
            artist = "Otro artista"
        ),
        Album(
            id = "2",
            cover = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg",
            name = "Otro album",
            artist = "Otro artista"
        ),
        Album(
            id = "2",
            cover = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg",
            name = "Ultimo album",
            artist = "Otro artista"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        LogoHeader("home_screen", navController = navController)

        Spacer(modifier = Modifier.height(32.dp))

        SectionHeader(
            title = "Álbumes",
            route = "album_create",
            navController = navController,
            true
        )

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(albums) { album ->
                    SecondaryAlbum(
                        title = album.name,
                        subtitle = album.artist,
                        cover = album.cover,
                        onClick = {
                            navController.navigate("album_detail/${album.id}?origin=album_screen")
                        }
                    )
                }
            }
        }
    }
}

