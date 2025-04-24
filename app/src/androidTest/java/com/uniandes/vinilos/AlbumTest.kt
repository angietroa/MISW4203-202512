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
        commonSteps.enterApplication(rule)
    }

    @Test
    fun showAlbumList() {
        commonSteps.clickOn(rule, "show_more_album")
        commonSteps.validateListIsVisible(rule, "album_list")
    }

    @Test
    fun showAlbumInHome() {
        commonSteps.searchSection(rule, "album_list")
        commonSteps.countItems(rule, "album_item")
    }

    @Test
    fun showAlbumDetailsFromList() {
        commonSteps.clickOn(rule, "show_more_album")
        commonSteps.clickFirstItem(rule, "album_item")
        commonSteps.validateElement(rule, "album_detail")
    }

    @Test
    fun showAlbumDetailsFromHome() {
        commonSteps.clickFirstItem(rule, "album_item")
        commonSteps.validateElement(rule, "album_detail")
    }
}
