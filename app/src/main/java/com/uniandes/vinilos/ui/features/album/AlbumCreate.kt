package com.uniandes.vinilos.ui.features.album

import android.util.Log
import android.widget.Toast
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.uniandes.vinilos.data.model.Album
import com.uniandes.vinilos.data.dto.AlbumRequestDTO
import com.uniandes.vinilos.ui.components.CustomInput
import com.uniandes.vinilos.ui.components.FormButtons
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.models.InputField
import com.uniandes.vinilos.viewmodel.AlbumViewModel

data class ApiError(
    val statusCode: Int,
    val message: String
)

@Composable
fun AlbumCreate(navController: NavHostController) {
    val viewModel: AlbumViewModel = viewModel()

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

        if (fieldsWithErrors > 0) return

        fun formatReleaseDate(year: String): String {
            return "${year}-08-01T00:00:00-05:00"
        }

        val formattedReleaseDate = formData["releaseDate"]?.let { formatReleaseDate(it) } ?: ""

        val albumDTO = AlbumRequestDTO(
            name = formData["name"] ?: "",
            cover = formData["cover"] ?: "",
            releaseDate = formattedReleaseDate,
            genre = formData["genre"] ?: "",
            recordLabel = formData["recordLabel"] ?: "",
            description = formData["description"] ?: ""
        )

        viewModel.createAlbum(
            album = albumDTO,
            onSuccess = {
                Log.d("AlbumCreate", "Álbum creado con éxito")
                navController.popBackStack()
            },
            onError = { e ->
                Log.e("AlbumCreate", "Error al crear álbum", e)

                // Aquí manejas el error si el servidor devuelve un error de validación
                val errorResponse = e.message?.let { parseErrorResponse(it) }
                errorResponse?.let { showToastError(it, navController) }
            }
        )
    }

    LazyColumn(
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

// Función para analizar la respuesta de error
fun parseErrorResponse(jsonResponse: String): ApiError? {
    return try {
        Gson().fromJson(jsonResponse, ApiError::class.java)
    } catch (e: Exception) {
        null
    }
}

// Función para mostrar el error al usuario
fun showToastError(error: ApiError, navController: NavHostController) {
    if (error.statusCode == 400) {
        // Mostrar notificación de error
        Toast.makeText(navController.context, error.message, Toast.LENGTH_LONG).show()
    }
}
