package com.uniandes.vinilos.ui.features.album

import com.uniandes.vinilos.R
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
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
import com.uniandes.vinilos.data.dto.ApiError
import com.uniandes.vinilos.ui.components.CustomInput
import com.uniandes.vinilos.ui.components.FormButtons
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.models.InputField
import com.uniandes.vinilos.viewmodel.AlbumViewModel
import androidx.compose.ui.platform.LocalContext

@Composable
fun AlbumCreate(navController: NavHostController) {
    val viewModel: AlbumViewModel = viewModel()
    val context = LocalContext.current
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val fields = remember {
        mutableStateListOf(
            InputField("name", "Nombre", "Nombre del álbum", KeyboardType.Text, ""),
            InputField("cover", "Portada", "URL de la portada", KeyboardType.Uri, ""),
            InputField("releaseDate", "Año de lanzamiento", "Ej: 2025", KeyboardType.Number, ""),
            InputField("genre", "Género", "Ej: Rock", KeyboardType.Text, ""),
            InputField("recordLabel", "Sello discografico", "Ej: Sony Music", KeyboardType.Text, ""),
            InputField("description", "Descripción", "Descripción del álbum", KeyboardType.Text, ""),
        )
    }

    fun formatReleaseDate(year: String): String {
        return "${year}-08-20T00:00:00-05:00"
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

        val year = formData["releaseDate"] ?: ""
        if (!year.matches(Regex("\\d{4}"))) {
            val index = fields.indexOfFirst { it.key == "releaseDate" }
            if (index != -1) {
                fields[index] = fields[index].copy(showError = true)
            }
            errorMessage.value = "El año debe contener solo 4 dígitos numéricos"
            return
        }

        val formattedReleaseDate = formatReleaseDate(year)

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
                Toast.makeText(context, "Álbum creado exitosamente", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            onError = { e ->
                Log.e("AlbumCreate", "Error al crear álbum", e)

                val errorBody = (e as? retrofit2.HttpException)?.response()?.errorBody()?.string()
                val errorResponse = errorBody?.let {
                    try {
                        Gson().fromJson(it, ApiError::class.java)
                    } catch (ex: Exception) {
                        Log.e("AlbumCreate", "Error al parsear el cuerpo de error", ex)
                        null
                    }
                }

                if (errorResponse?.statusCode == 400) {
                    val translated = when {
                        errorResponse.message.contains("ValidationError") &&
                                errorResponse.message.contains("\"genre\" must be one of") -> {
                            context.getString(R.string.genre_validation_error)
                        }
                        errorResponse.message.contains("ValidationError") &&
                                errorResponse.message.contains("\"recordLabel\" must be one of") -> {
                            context.getString(R.string.record_label_validation_error)
                        }
                        else -> errorResponse.message
                    }

                    errorMessage.value = translated
                } else {
                    errorMessage.value = context.getString(R.string.unknown_error)
                }
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
                    val filteredValue = if (field.key == "releaseDate") {
                        newValue.filter { it.isDigit() }
                    } else {
                        newValue
                    }
                    fields[index] = field.copy(value = filteredValue, showError = false)
                },
                keyboardType = field.keyboardType,
                showError = field.showError
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            FormButtons(
                routeBack = "album_screen",
                navController = navController,
                isAdd = false,
                onClickCreate = { handleOnClickCreate() },
                errorMessage = errorMessage.value,
                onToastShown = { errorMessage.value = null }
            )
        }
    }
}
