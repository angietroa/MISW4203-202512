package com.uniandes.vinilos.ui.features.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.components.SectionHeader
import com.uniandes.vinilos.data.model.Artist
import com.uniandes.vinilos.ui.components.SecondaryArtist

@Composable
fun ArtistScreen(navController: NavHostController) {
    val artists = listOf(
        Artist(
            id = "1",
            image = "https://radionacional-v3.s3.amazonaws.com/s3fs-public/styles/portadas_relaciona_4_3/public/senalradio/articulo-noticia/galeriaimagen/lospetitfellas_2020_5.jpg?h=34515be3&itok=kwWtG-VK",
            name = "Los Petit Fellas",
        ),
        Artist(
            id = "2",
            image = "https://s2.abcstatics.com/abc/www/multimedia/gente/2024/01/17/ariana-grande-kVxG-U601140978351bzB-1200x840@abc.jpg",
            name = "Ariana Grande",
        ),
        Artist(
            id = "3",
            image = "https://i.scdn.co/image/ab6761610000e5eb330d24db775125dcec2c7b4c",
            name = "Juanes"
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
            title = "Artistas",
            route = "artist_create",
            navController = navController,
            tag = "create_artist",
            isList = true
        )
        Spacer(modifier = Modifier.height(18.dp))

        LazyColumn (
            modifier = Modifier.testTag("artist_list"),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(artists) { artist ->
                SecondaryArtist(
                    modifier = Modifier.testTag("artist_item"),
                    title = artist.name,
                    cover = artist.image,
                    onClick = {
                        navController.navigate("artist_detail")
                    }
                )
            }
        }
    }
}

