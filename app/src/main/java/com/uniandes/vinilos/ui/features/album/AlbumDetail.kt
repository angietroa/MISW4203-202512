package com.uniandes.vinilos.ui.features.album

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.uniandes.vinilos.ui.components.LogoHeader
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.ui.components.DetailField
import com.uniandes.vinilos.viewmodel.AlbumViewModel
import java.time.Instant
import java.time.ZoneOffset

@SuppressLint("NewApi")
@Composable
fun AlbumDetail(albumId: String, origin: String, navController: NavHostController) {
    val viewModel: AlbumViewModel = viewModel()

    LaunchedEffect(albumId) {
        viewModel.loadAlbumById(albumId.toInt())
    }

    val selectedAlbumState = viewModel.selectedAlbumState.collectAsState().value


    when {
        selectedAlbumState.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        selectedAlbumState.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${selectedAlbumState.error}")
            }
        }
        selectedAlbumState.album != null -> {
            AlbumDetailContent(album = selectedAlbumState.album, origin = origin, navController = navController)
        }
    }
}

@Composable
fun AlbumDetailContent(album: Album, origin: String, navController: NavHostController) {
    val painter = rememberAsyncImagePainter(model = album.cover)
    val releaseYear = Instant.parse(album.releaseDate).atZone(ZoneOffset.UTC).year.toString()
    val performers = if (album.performers.isNotEmpty()) album.performers.joinToString(", ") { it.name } else "No hay artista registrado."
    val tracks = if (album.tracks.isNotEmpty()) album.tracks.joinToString(", ") { it.name } else "No hay canciones registradas."
    val comments = if (album.comments.isNotEmpty()) album.comments.joinToString(" - ") { it.description } else "No hay comentarios."

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .testTag("album_detail")
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            LogoHeader(origin, navController = navController)

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painter,
                    contentDescription = "Portada del álbum ${album.name}",
                    modifier = Modifier
                        .size(115.dp)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(13.dp))

                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {
                    Column {
                        Text(
                            text = album.name,
                            fontSize = 19.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Start
                        )

                        Text(
                            text = performers,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.tertiary,
                            textAlign = TextAlign.Start
                        )
                    }

                    Button(
                        onClick = { navController.navigate("album_create") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF059BFF).copy(alpha = 0.4f),
                            contentColor = MaterialTheme.colorScheme.tertiary
                        ),
                        border = BorderStroke(1.dp, Color(0xFF059BFF).copy(alpha = 0.4f)),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                        modifier = Modifier
                            .width(250.dp)
                            .height(30.dp),
                    ) {
                        Text(
                            text = "Agregar",
                            fontSize = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            DetailField("Año de lanzamiento", releaseYear)
            DetailField("Género", album.genre)
            DetailField("Sello discográfico", album.recordLabel)
            DetailField("Canciones", tracks)
            DetailField("Descripción", album.description)
            DetailField("Comentarios", comments)
        }
    }
}
