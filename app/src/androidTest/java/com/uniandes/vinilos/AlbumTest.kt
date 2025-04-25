package com.uniandes.vinilos

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumTest {

    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val commonSteps = CommonSteps()

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
}
