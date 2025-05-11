package com.uniandes.vinilos

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.javafaker.Faker
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4::class)
class AlbumTest {

    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val commonSteps = CommonSteps()
    private val faker = Faker(Locale.ENGLISH)

    @Before
    fun resetAppState() {
        //Given
        commonSteps.enterApplication(rule)
    }

    //E001: Consultar catalogo de albumes desde el listado
    @Test
    fun showAlbumList() {
        //When
        commonSteps.clickOn(rule, "show_more_album")
        //Then
        commonSteps.validateListIsVisible(rule, "album_list")
    }

    //E002: Consultar catalogo de albumes desde el home
    @Test
    fun showAlbumInHome() {
        //When
        commonSteps.searchSection(rule, "album_list")
        //Then
        commonSteps.countItems(rule, "album_item")
    }

    //E003: Consultar detalle de ambum desde el listado
    @Test
    fun showAlbumDetailsFromList() {
        //When
        commonSteps.clickOn(rule, "show_more_album")
        commonSteps.clickFirstItem(rule, "album_item")
        //Then
        commonSteps.validateElement(rule, "album_detail")
    }

    //E004: Consultar detalle de ambum desde el home
    @Test
    fun showAlbumDetailsFromHome() {
        //When
        commonSteps.clickFirstItem(rule, "album_item")
        //Then
        commonSteps.validateElement(rule, "album_detail")
    }

    //E009: Crear un album con datos correctos
    @Test
    fun createAlbumSuccessfully() {
        val albumTitle = faker.lorem().word()
        val albumCover = "https://picsum.photos/500/500?random=" + faker.number().digits(5)
        val releaseYear = faker.number().numberBetween(1950, 2025).toString()
        val genre = listOf("Classical", "Salsa", "Rock", "Folk").random()
        val recordLabel = listOf("Sony Music", "EMI", "Discos Fuentes", "Elektra", "Fania Records").random()
        val description = faker.lorem().paragraph(2)

        // Given
        commonSteps.clickOn(rule, "show_more_album")
        commonSteps.validateListIsVisible(rule, "album_list")
        commonSteps.clickItem(rule, "create_album")
        commonSteps.validateListIsVisible(rule, "album_create_form")
        commonSteps.fillInputField(rule, "name", albumTitle)
        commonSteps.fillInputField(rule, "cover", albumCover)
        commonSteps.fillInputField(rule, "releasedate", releaseYear)
        commonSteps.fillInputField(rule, "genre", genre)
        commonSteps.fillInputField(rule, "recordlabel", recordLabel)
        commonSteps.fillInputField(rule, "description", description)

        // When
        commonSteps.clickItem(rule, "create_button")

        // Then
        commonSteps.validateListIsVisible(rule, "album_list")
        commonSteps.clickByContentDescription(rule, "Atrás")
        runBlocking { delay(1000) }
        commonSteps.clickOn(rule, "show_more_album")
        commonSteps.validateListIsVisible(rule, "album_list")
        runBlocking { delay(3000) }
        commonSteps.scrollInListToATextElement(rule, "album_list", albumTitle)
        val albumFound = commonSteps.findTextInTagList(rule, "album_item", albumTitle)
        assert(albumFound) { "El álbum '$albumTitle' no aparece en la lista después de crearlo." }
    }

    //E010: Crear un album con datos invalidos
    @Test
    fun createAlbumWithFormErrors() {
        // Given
        commonSteps.clickOn(rule, "show_more_album")
        commonSteps.validateListIsVisible(rule, "album_list")
        commonSteps.clickItem(rule, "create_album")
        commonSteps.validateListIsVisible(rule, "album_create_form")
        commonSteps.fillInputField(rule, "name", "")
        commonSteps.fillInputField(rule, "cover", "")
        commonSteps.fillInputField(rule, "releasedate", "")
        commonSteps.fillInputField(rule, "genre", "")
        commonSteps.fillInputField(rule, "recordlabel", "")
        commonSteps.fillInputField(rule, "description", "")

        // When
        commonSteps.clickItem(rule, "create_button")

        // Then
        commonSteps.validateElement(rule, "input_error_name")
        commonSteps.validateElement(rule, "input_error_cover")
        commonSteps.validateElement(rule, "input_error_releasedate")
        commonSteps.validateElement(rule, "input_error_genre")
        commonSteps.validateElement(rule, "input_error_recordlabel")
        commonSteps.validateElement(rule, "input_error_description")
    }

}
