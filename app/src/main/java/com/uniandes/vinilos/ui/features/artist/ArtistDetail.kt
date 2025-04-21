package com.uniandes.vinilos.ui.features.artist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.uniandes.vinilos.ui.components.DetailField
import com.uniandes.vinilos.ui.components.LogoHeader

data class Artist(
    val cover: String,
    val name: String,
    val description: String,
    val birthday: String,
    val albums: String,
    val award: String
)

@Composable
fun ArtistDetail(artistId: String, origin: String, navController: NavHostController) {
    val artist = Artist(
        cover = "https://radionacional-v3.s3.amazonaws.com/s3fs-public/styles/portadas_relaciona_4_3/public/senalradio/articulo-noticia/galeriaimagen/lospetitfellas_2020_5.jpg?h=34515be3&itok=kwWtG-VK",
        name = "Los Petit Fellas",
        description = "LosPetitFellas es una banda colombiana formada en 2006 que fusiona géneros como jazz, funk, hip hop, soul y blues, conocida por sus letras introspectivas y socialmente conscientes.",
        birthday = "2006",
        albums = "Querido Frankie, Historias Mínimas, SOUVENIR, Formas para Perderse o I.D.E.A.S, 777",
        award = "Latin Grammy en 2018 en la categoría de Mejor Artista Nuevo."
    )
    val painter = rememberAsyncImagePainter(model = artist.cover)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            LogoHeader(origin, navController = navController)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 70.dp)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(26.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = Modifier
                        .size(226.dp)
                        .clip(CircleShape)
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = artist.name,
                fontSize = 32.sp,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            DetailField("Nombre", artist.name)
            DetailField("Descripción", artist.description)
            DetailField("Cumpleaños", artist.birthday)
            DetailField("Álbumes", artist.albums)
            DetailField("Premios", artist.award)
        }
    }
}
