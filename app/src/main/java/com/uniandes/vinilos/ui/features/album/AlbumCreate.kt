package com.uniandes.vinilos.ui.features.album

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.uniandes.vinilos.ui.components.CustomInput
import com.uniandes.vinilos.ui.components.FormButtons
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.models.InputField

@Composable
fun AlbumCreate(navController: NavHostController) {
    val fields = remember {
        mutableStateListOf(
            InputField("name", "Nombre", "Nombre del álbum", KeyboardType.Text, ""),
            InputField("performers", "Interpretes", "Nombre de los interpretes", KeyboardType.Text, ""),
            InputField("cover", "Portada", "URL de la portada", KeyboardType.Uri, ""),
            InputField("releaseDate", "Año de lanzamiento", "Ej: 2025", KeyboardType.Number, ""),
            InputField("genre", "Género", "Ej: Pop Latino", KeyboardType.Text, ""),
            InputField("recordLabel", "Sello discografico", "Ej: Sony Music", KeyboardType.Text, ""),
            InputField("tracks", "Canciones", "Canciones del álbum", KeyboardType.Text, ""),
            InputField("description", "Descripción", "Descripción del álbum", KeyboardType.Text, ""),
            InputField("comments", "Comentarios", "Comentarios del álbum", KeyboardType.Text, ""),
        )
    }

    fun handleOnClickCreate() {
        val formData = fields.associate { it.key to it.value }
        Log.d("AlbumCreate", "Formulario: $formData")

        val requiredKeys = fields.map { it.key }
        var fieldsWithErrors = 0

        requiredKeys.forEach { key ->
            if (formData[key].isNullOrBlank()) {
                fieldsWithErrors += 1
                val index = fields.indexOfFirst { it.key == key }
                if (index != -1) {
                    fields[index] = fields[index].copy(showError = true)
                }
            }
        }

        if (fieldsWithErrors > 0) {
            return
        } else {
            // Guardar datos
            val performers = formData.get("performers")?.split(",")?.map { it.trim() }
            val tracks = formData.get("tracks")?.split(",")?.map { it.trim() }
            val comments = formData.get("comments")?.split(",")?.map { it.trim() }

            Log.d("AlbumCreate", "performers: $performers")
            Log.d("AlbumCreate", "tracks: $tracks")
            Log.d("AlbumCreate", "comments: $comments")
        }
    }

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        item {
            LogoHeader("album_screen", navController = navController)
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Crear un nuevo álbum",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.ExtraBold),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        itemsIndexed(fields) { index, field ->
            CustomInput(
                label = field.label,
                placeholder = field.placeholder,
                value = field.value,
                onValueChange = { newValue ->
                    fields[index] = field.copy(value = newValue, showError = false)
                },
                keyboardType = field.keyboardType,
                showError = field.showError
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            FormButtons(
                "album_screen",
                navController = navController,
                false,
                { handleOnClickCreate() }
            )
        }
    }
}
