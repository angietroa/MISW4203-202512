package com.uniandes.vinilos.ui.features.album

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.components.SecondaryAlbum
import com.uniandes.vinilos.ui.components.SectionHeader
import com.uniandes.vinilos.viewmodel.AlbumViewModel

@Composable
fun AlbumScreen(
    navController: NavHostController,
    albumViewModel: AlbumViewModel = viewModel()
) {
    val albumState by albumViewModel.albumState.collectAsState()

    LaunchedEffect(key1 = true) {
        albumViewModel.loadAlbums()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .testTag("album_screen")
    ) {

        LogoHeader("home_screen", navController = navController)

        Spacer(modifier = Modifier.height(32.dp))

        SectionHeader(
            title = "Álbumes",
            route = "album_create",
            navController = navController,
            tag = "create_album",
            isList = true
        )

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.TopStart
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
                    LazyColumn(
                        modifier = Modifier.testTag("album_list"),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(albumState.albums) { album ->
                            SecondaryAlbum(
                                modifier = Modifier.testTag("album_item"),
                                title = album.name,
                                subtitle = album.performers.joinToString(", ") { performer -> performer.name },
                                cover = album.cover,
                                onClick = {
                                    navController.navigate("album_detail/${album.id}?origin=album_screen")
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
    }
}