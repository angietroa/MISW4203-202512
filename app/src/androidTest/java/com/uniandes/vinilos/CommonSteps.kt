package com.uniandes.vinilos

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import org.junit.Assert.assertTrue


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

    fun clickOnText(composeTestRule: ComposeTestRule, text: String) {
        composeTestRule
            .onNodeWithText(text, substring = false)
            .assertExists()
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

    fun clickItem(composeTestRule: ComposeTestRule, itemTag: String) {
        waitUntil(composeTestRule, itemTag)
        composeTestRule
            .onNodeWithTag(itemTag)
            .performClick()
    }

    fun clickByContentDescription(composeTestRule: ComposeTestRule, description: String) {
        composeTestRule.onNodeWithContentDescription(description).performClick()
    }

    fun fillInputField(composeTestRule: ComposeTestRule, fieldKey: String, text: String) {
        composeTestRule.onNodeWithTag("input_$fieldKey").performTextInput(text)
    }

    fun scrollInListToATextElement(composeTestRule: ComposeTestRule, itemTag: String, text: String) {
        try {
            composeTestRule.onNodeWithTag(itemTag)
                .performScrollToNode(hasText(text))
            println("Scrolled to album: $text")
        } catch (e: Exception) {
            println("No se pudo hacer scroll al álbum: ${e.message}")
        }
    }

    fun findTextInTagList(composeTestRule: ComposeTestRule, itemTag: String, text: String): Boolean {
        return composeTestRule.onAllNodesWithTag(itemTag)
            .fetchSemanticsNodes()
            .any { node ->
                val texts = node.config.getOrElse(SemanticsProperties.Text) { emptyList() }
                texts.any { it.text.contains(text) }
            }
    }

    fun countItemsAtLeast(composeTestRule: ComposeTestRule, itemTag: String, expectedCount: Int) {
        val items = composeTestRule
            .onAllNodesWithTag(itemTag)
            .fetchSemanticsNodes()

        assertTrue(
            "Se esperaban al menos $expectedCount elementos con tag '$itemTag', pero se encontraron ${items.size}",
            items.size >= expectedCount
        )
    }

    fun clickFirstButtonWithText(composeTestRule: ComposeTestRule, buttonText: String) {
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodes(hasText(buttonText)).fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule
            .onAllNodes(hasText(buttonText))
            .onFirst()
            .assertIsDisplayed()
            .performClick()
    }

    fun assertNodeWithTagIsNotVisible(composeTestRule: ComposeTestRule, tag: String) {
        composeTestRule.onNodeWithTag(tag).assertDoesNotExist()
    }

    fun selectDropdownOption(composeTestRule: ComposeTestRule, text: String) {
        composeTestRule.onNodeWithText(text).performClick()
    }
}
