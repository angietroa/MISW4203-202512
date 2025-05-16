package com.uniandes.vinilos.ui.features.collector

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uniandes.vinilos.ui.components.CustomDropdown
import com.uniandes.vinilos.ui.components.CustomInput
import com.uniandes.vinilos.ui.components.FormButtons
import com.uniandes.vinilos.ui.components.LogoHeader

data class Option(val label: String, val value: String)

@Composable
fun CollectorAdd(collectorId: String, navController: NavHostController) {
    val albums = listOf(
        Option(label = "Rock", value = "Rock"),
        Option(label = "Pop", value = "Pop"),
        Option(label = "Jazz", value = "Jazz")
    )
    val status = listOf(
        Option(label = "Activo", value = "active"),
        Option(label = "Inactivo", value = "inactive")
    )
    var selectedAlbum by remember { mutableStateOf(albums.first()) }
    var selectedStatus by remember { mutableStateOf(status[0]) }

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

        CustomDropdown(
            label = "Álbum",
            options = albums,
            selectedOption = selectedAlbum,
            onOptionSelected = { selectedAlbum = it },
            optionLabel = { it.label }
        )

        CustomDropdown(
            label = "Estado",
            options = status,
            selectedOption = selectedStatus,
            onOptionSelected = { selectedStatus = it },
            optionLabel = { it.label }
        )

        CustomInput(
            label = "Precio",
            placeholder = "Precio",
            value = "",
            onValueChange = {},
            keyboardType = KeyboardType.Text
        )

        FormButtons(
            "collector_screen",
            navController = navController,
            true,
            { handleOnClickCreate() }
        )
    }
}
