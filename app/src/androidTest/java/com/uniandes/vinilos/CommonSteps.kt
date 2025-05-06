package com.uniandes.vinilos

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import junit.framework.TestCase.assertTrue


class CommonSteps {
    private fun waitUntil(composeTestRule: ComposeTestRule, tag: String) {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodesWithTag(tag)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
    }

    fun validateElement(composeTestRule: ComposeTestRule, tag: String) {
        composeTestRule
            .onNodeWithTag(tag)
            .assertExists()
            .assertIsDisplayed()
    }

    fun enterApplication(composeTestRule: ComposeTestRule) {
        composeTestRule.waitUntil(
            timeoutMillis = 6000,
            condition = {
                composeTestRule.onAllNodesWithTag("home_screen").fetchSemanticsNodes().isNotEmpty()
            }
        )
    }

    fun clickOn(composeTestRule: ComposeTestRule, tag: String) {
        composeTestRule
            .onNodeWithTag(tag)
            .assertIsDisplayed()
            .performClick()
    }

    fun validateListIsVisible(composeTestRule: ComposeTestRule, listTag: String) {
        waitUntil(composeTestRule, listTag)
        validateElement(composeTestRule, listTag)
    }

    fun searchSection (composeTestRule: ComposeTestRule, sectionTag: String) {
        composeTestRule.onNodeWithTag(sectionTag).assertIsDisplayed()
    }

    fun countItems (composeTestRule: ComposeTestRule, itemTag: String) {
        val items = composeTestRule
            .onAllNodesWithTag(itemTag)
            .fetchSemanticsNodes()

        assertTrue(items.isNotEmpty())
    }

    fun clickFirstItem(composeTestRule: ComposeTestRule, itemTag: String) {
        waitUntil(composeTestRule, itemTag)
        composeTestRule
            .onAllNodesWithTag(itemTag)
            .onFirst()
            .performClick()
    }
}
