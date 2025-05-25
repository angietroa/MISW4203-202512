package com.uniandes.vinilos.ui.features.artist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.uniandes.vinilos.ui.components.DetailField
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.viewmodel.ArtistViewModel
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ArtistDetail(artistId: String, origin: String, navController: NavHostController) {
    val viewModel: ArtistViewModel = viewModel()

    LaunchedEffect(artistId) {
        viewModel.loadArtistById(artistId.toInt())
    }

    val selectedArtistState by viewModel.selectedArtistState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("artist_detail")
    ) {
        Column {
            LogoHeader(origin, navController = navController)
        }

        when {
            selectedArtistState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            selectedArtistState.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Error: ${selectedArtistState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            selectedArtistState.artist != null -> {
                val artist = selectedArtistState.artist!!
                val painter = rememberAsyncImagePainter(model = artist.image)

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
                        modifier = Modifier.fillMaxWidth().testTag("artist_name").semantics { contentDescription = artist.name }
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    DetailField("Nombre", artist.name)
                    DetailField("Descripción", artist.description)
                    DetailField(
                        "Cumpleaños",
                        artist.birthDate.let {
                            val date = Instant.parse(it).atZone(ZoneOffset.UTC).toLocalDate()
                            val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale("es"))
                            date.format(formatter)
                        } ?: "N/A"
                    )
                    DetailField(
                        "Álbumes",
                        if (artist.albums.isNullOrEmpty()) {
                            "No hay información disponible"
                        } else {
                            artist.albums.joinToString(", ") { it.name }
                        }
                    )
                }
            }
        }
    }
}
