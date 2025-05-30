package com.uniandes.vinilos

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import com.github.javafaker.Faker
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class ArtistTest {

    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val commonSteps = CommonSteps()
    private val faker = Faker(Locale.ENGLISH)

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

    //E013: Crear un artista con datos correctos
    @Test
    fun createArtistSuccessfully() {
        val artistName = faker.artist().name()
        val artistImage = "https://picsum.photos/500/500?random=" + faker.number().digits(5)
        val description = faker.lorem().paragraph(2)

        val day = faker.number().numberBetween(1, 28).toString().padStart(2, '0')
        val month = faker.number().numberBetween(1, 12).toString().padStart(2, '0')
        val year = faker.number().numberBetween(1950, 2020).toString()
        val birthdate = "$day$month$year"

        commonSteps.clickOn(rule, "show_more_artist")
        commonSteps.validateListIsVisible(rule, "artist_list")
        commonSteps.clickItem(rule, "create_artist")

        commonSteps.fillInputField(rule, "name", artistName)
        commonSteps.fillInputField(rule, "cover", artistImage)
        commonSteps.fillInputField(rule, "description", description)
        commonSteps.fillInputField(rule, "birthday", birthdate)

        // When
        commonSteps.clickItem(rule, "create_button")

        // Then
        commonSteps.validateListIsVisible(rule, "artist_list")
    }

    //E014: Crear un artista con datos invalidos
    @Test
    fun createArtistWithFormErrors() {
        commonSteps.clickOn(rule, "show_more_artist")
        commonSteps.validateListIsVisible(rule, "artist_list")
        commonSteps.clickItem(rule, "create_artist")

        // When
        commonSteps.fillInputField(rule, "name", "")
        commonSteps.fillInputField(rule, "cover", "")
        commonSteps.fillInputField(rule, "description", "")
        commonSteps.fillInputField(rule, "birthday", "")

        // When
        commonSteps.clickItem(rule, "create_button")

        // Then
        commonSteps.validateElement(rule, "input_error_name")
        commonSteps.validateElement(rule, "input_error_cover")
        commonSteps.validateElement(rule, "input_error_description")
        commonSteps.validateElement(rule, "input_error_birthday")
    }

}
