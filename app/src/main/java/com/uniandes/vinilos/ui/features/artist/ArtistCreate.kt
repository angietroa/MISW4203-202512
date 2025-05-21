package com.uniandes.vinilos.ui.features.artist

import com.uniandes.vinilos.R
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import com.google.gson.Gson
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.uniandes.vinilos.data.dto.ApiError
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.uniandes.vinilos.data.dto.ArtistRequestDTO
import com.uniandes.vinilos.ui.components.BirthdayInput
import com.uniandes.vinilos.viewmodel.ArtistViewModel
import com.uniandes.vinilos.ui.components.FormButtons
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.ui.components.CustomInput
import com.uniandes.vinilos.ui.models.InputField

@Composable
fun ArtistCreate(navController: NavHostController) {
    val viewModel: ArtistViewModel = viewModel()
    val context = LocalContext.current
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val birthdayField = remember { mutableStateOf(TextFieldValue("")) }

    val fields = remember {
        mutableStateListOf(
            InputField("name", "Nombre", "Ej: Carlos Vives", KeyboardType.Text, ""),
            InputField("cover", "Imagen", "URL de la imagen", KeyboardType.Uri, ""),
            InputField("description", "Descripción", "Descripción del artista", KeyboardType.Text, "")
        )
    }

    val nameError = remember { mutableStateOf(false) }
    val imageError = remember { mutableStateOf(false) }
    val descriptionError = remember { mutableStateOf(false) }
    val birthdayError = remember { mutableStateOf(false) }

    fun convertDateToISO(dateStr: String): String? {

        return try {
            val parts = dateStr.split("-")
            if (parts.size == 3) {
                val day = parts[0].padStart(2, '0')
                val month = parts[1].padStart(2, '0')
                val year = parts[2]
                "$year-$month-$day"
            } else null
        } catch (e: Exception) {
            null
        }
    }

    fun handleOnClickCreate() {
        val name = fields.getOrNull(0)?.value?.trim() ?: ""
        val image = fields.getOrNull(1)?.value?.trim() ?: ""
        val description = fields.getOrNull(2)?.value?.trim() ?: ""
        val birthDate = birthdayField.value.text.trim()

        Log.d("BirthdayDebug", "birthDate raw value: '$birthDate'")

        // Validaciones
        nameError.value = name.isEmpty()
        imageError.value = image.isEmpty()
        descriptionError.value = description.isEmpty()
        birthdayError.value = birthDate.isEmpty()

        if (nameError.value || imageError.value || descriptionError.value || birthdayError.value) {
            Toast.makeText(context, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        val isoBirthDate = convertDateToISO(birthDate)
        if (isoBirthDate == null) {
            birthdayError.value = true
            Toast.makeText(context, "Fecha inválida, use formato DD-MM-YYYY", Toast.LENGTH_SHORT).show()
            return
        }

        val artistDTO = ArtistRequestDTO(name, image, description, isoBirthDate)

        viewModel.createArtist(
            artist = artistDTO,
            onSuccess = {
                Toast.makeText(context, "Artista creado exitosamente", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            onError = { e ->
                Log.e("ArtistCreate", "Error al crear artista", e)
                val errorBody = (e as? retrofit2.HttpException)?.response()?.errorBody()?.string()
                val errorResponse = errorBody?.let {
                    try {
                        Gson().fromJson(it, ApiError::class.java)
                    } catch (ex: Exception) {
                        Log.e("ArtistCreate", "Error al parsear el cuerpo de error", ex)
                        null
                    }
                }

                errorMessage.value = errorResponse?.message ?: context.getString(R.string.unknown_error)
            }
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
                val showError = when (field.key) {
                    "name" -> nameError.value
                    "cover" -> imageError.value
                    "description" -> descriptionError.value
                    else -> false
                }
                CustomInput(
                    label = field.label,
                    placeholder = field.placeholder,
                    value = field.value,
                    onValueChange = { newValue ->
                        fields[index] = field.copy(value = newValue)
                    },
                    keyboardType = field.keyboardType,
                    showError = showError,
                    key = field.key
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            BirthdayInput(
                value = birthdayField.value,
                onValueChange = { birthdayField.value = it },
                showError = birthdayError.value
            )
        }

        FormButtons(
            "artist_screen",
            navController = navController,
            false,
            { handleOnClickCreate() }
        )
    }
}





