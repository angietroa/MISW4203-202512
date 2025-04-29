package com.uniandes.vinilos.ui.features.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.uniandes.vinilos.ui.components.FormButtons
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.components.CustomInput
import com.uniandes.vinilos.ui.models.InputField

@Composable
fun ArtistCreate(navController: NavHostController) {
    val fields = remember {
        mutableStateListOf(
            InputField("name", "Nombre", "Ej: Carlos Vives", KeyboardType.Text, ""),
            InputField("cover", "Imagen", "URL de la imagen", KeyboardType.Uri, ""),
            InputField("description", "Descripción", "Descripción del artista", KeyboardType.Text, ""),
            InputField("birthday", "Cumpleaños", "DD-MM-YYYY", KeyboardType.Number, ""),
            InputField("albums", "Álbumes", "Álbumes", KeyboardType.Number, ""),
            InputField("award", "Premios", "Nombre de los premios", KeyboardType.Text, "")
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        LogoHeader("artist_screen", navController = navController)
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Crear un nuevo artista",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
            color = MaterialTheme.colorScheme.primary
        )

        Column(modifier = Modifier.padding(16.dp)) {
            fields.forEachIndexed { index, field ->
                CustomInput(
                    label = field.label,
                    placeholder = field.placeholder,
                    value = field.value,
                    onValueChange = { newValue ->
                        fields[index] = field.copy(value = newValue)
                    },
                    keyboardType = field.keyboardType
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        FormButtons("artist_screen", navController = navController)
    }
}
