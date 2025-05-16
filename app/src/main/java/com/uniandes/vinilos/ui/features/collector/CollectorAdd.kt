package com.uniandes.vinilos.ui.features.collector

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uniandes.vinilos.ui.components.CustomDropdown
import com.uniandes.vinilos.ui.components.CustomInput
import com.uniandes.vinilos.ui.components.FormButtons
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.viewmodel.AlbumViewModel

data class Option(val label: String, val value: String)

@Composable
fun CollectorAdd(collectorId: String, navController: NavHostController) {
    val albumViewModel: AlbumViewModel = viewModel()
    val albumState by albumViewModel.albumState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        albumViewModel.loadAlbums()
    }

    val status = listOf(
        Option(label = "Activo", value = "active"),
        Option(label = "Inactivo", value = "inactive")
    )
    // Convert albums from the ViewModel to your Option format
    val albumOptions = albumState.albums.map { album ->
        Option(label = album.name, value = album.id.toString())
    }

    // Selected option states
    var selectedAlbum by remember { mutableStateOf<Option?>(null) }
    var selectedStatus by remember { mutableStateOf(status[0]) }
    var price by remember { mutableStateOf("") }

    LaunchedEffect(albumOptions) {
        if (albumOptions.isNotEmpty() && selectedAlbum == null) {
            selectedAlbum = albumOptions.first()
        }
    }

    fun handleOnClickCreate() {
        Log.d("Dropdown", "Álbum seleccionado: $selectedAlbum")
        Log.d("Dropdown", "Estado seleccionado: ${selectedStatus.value}")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        LogoHeader("collector_screen", navController = navController)
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = "Agregar álbum",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Show loading indicator when loading albums
        if (albumState.isLoading) {
            CircularProgressIndicator()
        } else if (albumState.error != null) {
            // Show error message if there's an error
            Text(
                text = albumState.error ?: "Error desconocido",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else if (albumOptions.isEmpty()) {
            // Show message if no albums available
            Text(
                text = "No hay álbumes disponibles",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            // Album dropdown
            selectedAlbum?.let { selected ->
                CustomDropdown(
                    label = "Álbum",
                    options = albumOptions,
                    selectedOption = selected,
                    onOptionSelected = { selectedAlbum = it },
                    optionLabel = { it.label }
                )
            }

            // Status dropdown (keeping your existing implementation)
            CustomDropdown(
                label = "Estado",
                options = status,
                selectedOption = selectedStatus,
                onOptionSelected = { selectedStatus = it },
                optionLabel = { it.label }
            )

            // Price input (keeping your existing implementation)
            CustomInput(
                label = "Precio",
                placeholder = "Precio",
                value = price,
                onValueChange = { price = it },
                keyboardType = KeyboardType.Number
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        FormButtons(
            "collector_screen",
            navController = navController,
            true,
            { handleOnClickCreate() }
        )
    }
}
