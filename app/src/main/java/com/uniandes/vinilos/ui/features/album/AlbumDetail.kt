package com.uniandes.vinilos.ui.features.album

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.uniandes.vinilos.ui.components.LogoHeader
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.ui.components.DetailField
import com.uniandes.vinilos.ui.components.FormButtons

@Composable
fun AlbumDetail(albumId: String, origin: String, navController: NavHostController) {
    val album = Album(
        id = "1",
        cover = "https://i.scdn.co/image/ab67616d0000b273c05fd08ca89f68bdfef5a21e",
        name = "Trece",
        artist = "Andrés Cepeda",
        performers = listOf(
            Artist(
                id = "1",
                name = "Andres Cepeda",
                image = "https://i.scdn.co/image/ab67616d0000b273c05fd08ca89f68bdfef5a21e"
            )
        ),
        releaseDate = "",
        description = ""
    )

    val painter = rememberAsyncImagePainter(model = album.cover)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Column {
            LogoHeader(origin, navController = navController)

            Spacer(modifier = Modifier.height(32.dp))

            Row (
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

                Column (
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
                            text = album.performers
                                .map{ performer -> performer.name }
                                .joinToString(" ,"),
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.primary,
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
                        modifier = Modifier.width(250.dp).height(30.dp),
                    ) {
                        Text(
                            text = "Agregar",
                            fontSize = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            DetailField("Año de lanzamiento", "2025")
            DetailField("Género", "Pop latino")
            DetailField("Sello discografico", "Sony Music")
            DetailField("Canciones", "Una, dos, tres")
            DetailField("Descripción", "Descr")
            DetailField("Comentarios", "Comentariosss")
        }
    }
}