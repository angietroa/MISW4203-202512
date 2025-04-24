package com.uniandes.vinilos

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    private val commonSteps = CommonSteps()

    @Test
    fun showAlbumList() {
        commonSteps.enterApplication(rule)
        commonSteps.clickOn(rule, "show_more_album")
        commonSteps.validateListIsVisible(rule, "album_list")
    }

    @Test
    fun showAlbumInHome() {
        commonSteps.enterApplication(rule)
        commonSteps.searchSection(rule, "album_list")
        commonSteps.countItems(rule, "album_item")
    }

    @Test
    fun showAlbumDetailsFromList() {
        commonSteps.enterApplication(rule)
        commonSteps.clickOn(rule, "show_more_album")
        commonSteps.clickFirstItem(rule, "album_item")
        commonSteps.validateElement(rule, "album_detail")
    }

    @Test
    fun showAlbumDetailsFromHome() {
        commonSteps.enterApplication(rule)
        commonSteps.clickFirstItem(rule, "album_item")
        commonSteps.validateElement(rule, "album_detail")
    }
}
