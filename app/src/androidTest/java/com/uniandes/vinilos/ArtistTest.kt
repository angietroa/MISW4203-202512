package com.uniandes.vinilos

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistTest {

    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val commonSteps = CommonSteps()

    @Before
    fun resetAppState() {
        //Given
        commonSteps.enterApplication(rule)
    }

    //E005: Consultar catalogo de artistas desde el listado
    @Test
    fun showArtistList() {
        //When
        commonSteps.clickOn(rule, "show_more_artist")
        //Then
        commonSteps.validateListIsVisible(rule, "artist_list")
    }

    //E006: Consultar catalogo de artistas desde el home
    @Test
    fun showArtistInHome() {
        //When
        commonSteps.searchSection(rule, "artist_list")
        //Then
        commonSteps.countItems(rule, "artist_item")
    }

    //E007: Consultar detalle de artistas desde el listado
    @Test
    fun showArtistDetailsFromList() {
        //When
        commonSteps.clickOn(rule, "show_more_artist")
        commonSteps.clickFirstItem(rule, "artist_list")
        //Then
        commonSteps.validateElement(rule, "artist_detail")
    }

    //E008: Consultar detalle de artitas desde el home
    @Test
    fun showAlbumDetailsFromHome() {
        //When
        commonSteps.clickFirstItem(rule, "artist_item")
        //Then
        commonSteps.validateElement(rule, "artist_detail")
    }
//
//    // E009: Acceder al formulario de creación de álbum
//    @Test
//    fun navigateToAlbumCreateForm() {
//        // When
//        commonSteps.clickOn(rule, "show_more_album")
//        commonSteps.clickOn(rule, "create_album")
//
//        // Then
//        commonSteps.validateElement(rule, "album_create_form")
//    }
//
//    // E010: Validar campos requeridos en formulario
//    @Test
//    fun validateRequiredFields() {
//        // When
//        commonSteps.clickOn(rule, "show_more_album")
//        commonSteps.clickOn(rule, "create_album")
//        rule.onNodeWithText("Crear").performClick()
//
//        // Then
//        commonSteps.validateFieldWithError(rule, "name")
//        commonSteps.validateFieldWithError(rule, "releaseDate")
//        commonSteps.validateFieldWithError(rule, "genre")
//    }
//
//    // E011: Validar formato de año en formulario
//    @Test
//    fun validateYearFormat() {
//        // When
//        commonSteps.clickOn(rule, "show_more_album")
//        commonSteps.clickOn(rule, "create_album_button")
//
//        // Fill required fields except year
//        commonSteps.fillTextField(rule, "name", "Test Album")
//        commonSteps.fillTextField(rule, "performers", "Test Artist")
//        commonSteps.fillTextField(rule, "cover", "https://example.com/cover.jpg")
//        commonSteps.fillTextField(rule, "releaseDate", "202") // Invalid year (less than 4 digits)
//        commonSteps.fillTextField(rule, "genre", "Classical")
//        commonSteps.fillTextField(rule, "recordLabel", "Sony Music")
//
//        commonSteps.clickOn(rule, "create_button")
//
//        // Then
//        commonSteps.validateFieldWithError(rule, "releaseDate")
//        commonSteps.validateErrorMessage(rule, "El año debe contener solo 4 dígitos numéricos")
//    }
//
//    // E012: Crear álbum exitosamente
//    @Test
//    fun createAlbumSuccessfully() {
//        // When
//        commonSteps.clickOn(rule, "show_more_album")
//        commonSteps.clickOn(rule, "create_album_button")
//
//        // Fill all required fields
//        commonSteps.fillTextField(rule, "name", "Test Album")
//        commonSteps.fillTextField(rule, "performers", "Test Artist")
//        commonSteps.fillTextField(rule, "cover", "https://example.com/cover.jpg")
//        commonSteps.fillTextField(rule, "releaseDate", "2025")
//        commonSteps.fillTextField(rule, "genre", "Classical")
//        commonSteps.fillTextField(rule, "recordLabel", "Sony Music")
//        commonSteps.fillTextField(rule, "description", "Test description")
//
//        commonSteps.clickOn(rule, "create_button")
//
//        // Then
//        // Verify we're back on album list screen
//        commonSteps.validateElement(rule, "album_screen")
//        // Check for success toast (this might need additional methods in CommonSteps)
//        commonSteps.validateToastMessage(rule, "Álbum creado exitosamente")
//    }
//
//    // E013: Validar error al crear álbum con género inválido
//    @Test
//    fun validateGenreError() {
//        // When
//        commonSteps.clickOn(rule, "show_more_album")
//        commonSteps.clickOn(rule, "create_album_button")
//
//        // Fill required fields with invalid genre
//        commonSteps.fillTextField(rule, "name", "Test Album")
//        commonSteps.fillTextField(rule, "performers", "Test Artist")
//        commonSteps.fillTextField(rule, "cover", "https://example.com/cover.jpg")
//        commonSteps.fillTextField(rule, "releaseDate", "2025")
//        commonSteps.fillTextField(rule, "genre", "InvalidGenre") // Invalid genre
//        commonSteps.fillTextField(rule, "recordLabel", "Sony Music")
//
//        commonSteps.clickOn(rule, "create_button")
//
//        // Then
//        commonSteps.validateErrorMessage(rule, "El género debe ser uno de los siguientes")
//    }
//
//    // E014: Cancelar creación de álbum
//    @Test
//    fun cancelAlbumCreation() {
//        // When
//        commonSteps.clickOn(rule, "show_more_album")
//        commonSteps.clickOn(rule, "create_album_button")
//
//        // Fill some fields
//        commonSteps.fillTextField(rule, "name", "Test Album")
//
//        // Cancel creation
//        commonSteps.clickOn(rule, "cancel_button")
//
//        // Then
//        // Verify we're back on album list screen without creating album
//        commonSteps.validateElement(rule, "album_screen")
//    }
}
