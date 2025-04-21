package com.uniandes.vinilos.ui.features.collector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.uniandes.vinilos.data.model.Collector
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.components.MainCollector

@Composable
fun CollectorScreen(navController: NavHostController) {
    val collectors = listOf(
        Collector(
            id = "1",
            cover = "https://wallpapers.com/images/hd/neon-purple-4k-z32z5va3r8itqjvq.jpg",
            name = "Colección 1",
            albumCount = "5 Álbumes",
        ),
        Collector(
            id = "2",
            cover = "https://img.freepik.com/vector-gratis/fondo-luces-neon-realista_23-2148918046.jpg?semt=ais_hybrid&w=740",
            name = "Colección 2",
            albumCount = "5 Álbumes",
        ),
        Collector(
            id = "3",
            cover = "https://img.freepik.com/foto-gratis/fondo-neon-colorido-brillante_23-2150689404.jpg?semt=ais_hybrid&w=740",
            name = "Colección 3",
            albumCount = "5 Álbumes",
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
        Text(
            text = "Coleccionistas",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(collectors) { collector ->
                    MainCollector(
                        cover = collector.cover,
                        name = collector.name,
                        albumCount = collector.albumCount,
                        onClick = {
                            navController.navigate("collector_add/${collector.id}")
                        }
                    )
                }
            }
        }
    }
}

