package com.uniandes.vinilos.ui.features.collector

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.uniandes.vinilos.data.model.Collector
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.components.MainCollector
import com.uniandes.vinilos.ui.components.SecondaryCollector
import com.uniandes.vinilos.viewmodel.ArtistViewModel
import com.uniandes.vinilos.viewmodel.CollectorViewModel

@Composable
fun CollectorScreen(navController: NavHostController, collectorViewModel: CollectorViewModel = viewModel()) {

    val collectorState by collectorViewModel.collectorState.collectAsState()

    LaunchedEffect(Unit) {
        collectorViewModel.loadCollectors()
    }

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
            when {
                collectorState.isLoading -> {
                    CircularProgressIndicator()
                }
                collectorState.error != null -> {
                    Text(
                        text = "Error: ${collectorState.error}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                collectorState.collectors.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.testTag("collector_list"),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(collectorState.collectors) { collector ->
                            SecondaryCollector (
                                name = collector.name,
                                albumCount = collector.collectorAlbums.size.toString(),
                                onClick = {
                                    navController.navigate("collector_add/${collector.id}")
                                }
                            )
                        }
                    }
                }
                else -> {
                    Text(text = "No hay coleccionistas disponibles")
                }
            }
        }
    }
}

