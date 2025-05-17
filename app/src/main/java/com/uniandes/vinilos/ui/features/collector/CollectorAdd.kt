package com.uniandes.vinilos.ui.features.collector

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.NavHostController
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.gson.Gson
import com.uniandes.vinilos.R
import com.uniandes.vinilos.data.dto.ApiError
import com.uniandes.vinilos.data.dto.CollectorAlbumRequestDTO
import com.uniandes.vinilos.ui.components.CustomDropdown
import com.uniandes.vinilos.ui.components.CustomInput
import com.uniandes.vinilos.ui.components.FormButtons
import com.uniandes.vinilos.ui.components.LogoHeader
import com.uniandes.vinilos.viewmodel.AlbumViewModel
import com.uniandes.vinilos.viewmodel.CollectorViewModel

data class Option(val label: String, val value: String)

@Composable
fun CollectorAdd(collectorId: String, navController: NavHostController) {
    val viewModel: CollectorViewModel = viewModel()
    val context = LocalContext.current
    val albumViewModel: AlbumViewModel = viewModel()
    val albumState by albumViewModel.albumState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        albumViewModel.loadAlbums()
    }

    val status = listOf(
        Option(label = "Activo", value = "Active"),
        Option(label = "Inactivo", value = "Inactive")
    )

    val albumOptions = albumState.albums.map { album ->
        Option(label = album.name, value = album.id)
    }

    var selectedAlbum by remember { mutableStateOf<Option?>(null) }
    var selectedStatus by remember { mutableStateOf(status[0]) }
    var price by remember { mutableStateOf("") }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(albumOptions) {
        if (albumOptions.isNotEmpty() && selectedAlbum == null) {
            selectedAlbum = albumOptions.first()
        }
    }

    fun handleOnClickCreate() {
        if (selectedAlbum!!.value == "") {
            errorMessage.value = context.getString(R.string.album_prevalidation_error)
            return
        }
        if (price == "") {
            errorMessage.value = context.getString(R.string.price_prevalidation_error)
            return
        }
        if (price.toIntOrNull() == null) {
            errorMessage.value = context.getString(R.string.price_nonumber_error)
            return
        }

        val requestBody = CollectorAlbumRequestDTO(
            price = price.toInt(),
            status = selectedStatus.value
        )

        viewModel.createCollectoralbum(
            collectorId = collectorId,
            albumId = selectedAlbum!!.value,
            requestBody = requestBody,
            onSuccess = {
                Log.d("CollectorAlbum", "Álbum agregado al coleccionista con éxito")
                Toast.makeText(context, "Álbum agregado al coleccionista con éxito", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            },
            onError = { e ->
                Log.e("CollectorAlbum", "Error al agregar al coleccionista", e)

                val errorBody = (e as? retrofit2.HttpException)?.response()?.errorBody()?.string()
                val errorResponse = errorBody?.let {
                    try {
                        Gson().fromJson(it, ApiError::class.java)
                    } catch (ex: Exception) {
                        Log.e("CollectorAlbum", "Error al parsear el cuerpo de error", ex)
                        null
                    }
                }

                if (errorResponse?.statusCode == 400) {
                    val translated = when {
                        errorResponse.message.contains("ValidationError") &&
                                errorResponse.message.contains("\"status\" must be one of") -> {
                            context.getString(R.string.status_validation_error)
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

        if (albumState.isLoading) {
            CircularProgressIndicator()
        } else if (albumState.error != null) {
            Text(
                text = albumState.error ?: "Error desconocido",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else if (albumOptions.isEmpty()) {
            Text(
                text = "No hay álbumes disponibles",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            selectedAlbum?.let { selected ->
                CustomDropdown(
                    label = "Álbum",
                    options = albumOptions,
                    selectedOption = selected,
                    onOptionSelected = { selectedAlbum = it },
                    optionLabel = { it.label }
                )
            }

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
                value = price,
                onValueChange = { price = it },
                keyboardType = KeyboardType.Number
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        FormButtons(
            routeBack = "collector_screen",
            navController = navController,
            isAdd = true,
            onClickCreate = { handleOnClickCreate() },
            errorMessage = errorMessage.value,
            onToastShown = { errorMessage.value = null }
        )
    }
}
