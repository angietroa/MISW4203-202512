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

}
