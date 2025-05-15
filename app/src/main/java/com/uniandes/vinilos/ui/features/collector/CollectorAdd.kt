package com.uniandes.vinilos.ui.features.collector

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.uniandes.vinilos.ui.components.CustomDropdown
import com.uniandes.vinilos.ui.components.FormButtons
import com.uniandes.vinilos.ui.components.LogoHeader
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun CollectorAdd(collectorId: String, navController: NavHostController) {
    val albums = listOf("Rock", "Pop", "Jazz")
    var selectedAlbum by remember { mutableStateOf(albums.first()) }

    fun handleOnClickCreate() {
        Log.d("Dropdown", "Opción seleccionada: $selectedAlbum")
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
           label = "Album",
           options = albums,
           selectedOption = selectedAlbum,
           onOptionSelected = { selectedAlbum = it },
           modifier = Modifier.fillMaxWidth()
        )

        FormButtons(
            "collector_screen",
            navController = navController,
            true,
            { handleOnClickCreate() }
        )
    }
}