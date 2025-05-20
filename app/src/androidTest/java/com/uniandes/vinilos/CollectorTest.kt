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
class CollectorTest {
    @get:Rule val rule = createAndroidComposeRule<MainActivity>()
    private val commonSteps = CommonSteps()

    @Before
    fun resetAppState() {
        // Given
        commonSteps.enterApplication(rule)
    }

    // E011: Consultar catalogo de coleccionistas desde el home
    @Test
    fun showCollectorsFromHome() {
        // When: El usuario ve la lista de coleccionistas
        commonSteps.searchSection(rule, "collector_list")
        // Then: Se valida que la lista de coleccionistas tenga 2
        commonSteps.countItemsAtLeast(rule, "collector_item", 2)
    }

    // E012: Consultar catálogo de coleccionistas desde el botón "Ver más"
    @Test
    fun showCollectorsFromList() {

        // When: El usuario hace clic en el botón "Ver más" de coleccionistas
        commonSteps.clickOn(rule, "show_more_collector")

        // Then: Se valida que la lista de coleccionistas está visible
        commonSteps.validateListIsVisible(rule, "collector_list")

    }

    // E015: Asignar un álbum a coleccionista con datos correctos
    @Test
    fun assignAlbumToCollectorWithValidData() {
        // When: El usuario navega a los detalles del coleccionista y abre el formulario para agregar un álbum
        commonSteps.clickOn(rule, "show_more_collector")
        commonSteps.validateListIsVisible(rule, "collector_list")
        commonSteps.clickFirstButtonWithText(rule, "Agregar")

        // And: Selecciona un álbum y un estado
        commonSteps.clickItem(rule, "album_dropdown")
        commonSteps.selectDropdownOption(rule, "Poeta del pueblo")

        commonSteps.clickItem(rule, "status_dropdown")
        commonSteps.selectDropdownOption(rule, "Inactivo")

        // And: Llena el campo de precio
        commonSteps.clickOn(rule, "input_precio")
        commonSteps.fillInputField(rule, "precio", "15000")

        // And: Envía el formulario
        commonSteps.clickItem(rule, "create_button")

        // Then: Se regresa a la lista de coleccionistas, indicando éxito
        commonSteps.validateListIsVisible(rule, "collector_list")
    }

    // E016: Asignar un álbum a coleccionista con datos incorrectos
    @Test
    fun assignAlbumToCollectorWithInvalidData() {
        // When: El usuario navega a los detalles del coleccionista y abre el formulario para agregar un álbum
        commonSteps.clickOn(rule, "show_more_collector")
        commonSteps.validateListIsVisible(rule, "collector_list")
        commonSteps.clickFirstButtonWithText(rule, "Agregar")

        // And: Selecciona un álbum y un estado
        commonSteps.clickItem(rule, "album_dropdown")
        commonSteps.selectDropdownOption(rule, "Poeta del pueblo")

        commonSteps.clickItem(rule, "status_dropdown")
        commonSteps.selectDropdownOption(rule, "Inactivo")

        // And: Llena el campo de precio
        commonSteps.clickOn(rule, "input_precio")
        commonSteps.fillInputField(rule, "precio", "abcd")

        // And: Envía el formulario
        commonSteps.clickItem(rule, "create_button")
        commonSteps.assertNodeWithTagIsNotVisible(rule,"collector_list")
    }

    // E016: Asignar un álbum a coleccionista con datos incorrectos
    @Test
    fun assignAlbumToCollectorWithInvalidDataEmptyPrice() {
        // When: El usuario navega a los detalles del coleccionista y abre el formulario para agregar un álbum
        commonSteps.clickOn(rule, "show_more_collector")
        commonSteps.validateListIsVisible(rule, "collector_list")
        commonSteps.clickFirstButtonWithText(rule, "Agregar")

        // And: Selecciona un álbum y un estado
        commonSteps.clickItem(rule, "album_dropdown")
        commonSteps.selectDropdownOption(rule, "Poeta del pueblo")

        commonSteps.clickItem(rule, "status_dropdown")
        commonSteps.selectDropdownOption(rule, "Inactivo")

        // And: Llena el campo de precio
        commonSteps.clickOn(rule, "input_precio")
        commonSteps.fillInputField(rule, "precio", " ")

        // And: Envía el formulario
        commonSteps.clickItem(rule, "create_button")
        commonSteps.assertNodeWithTagIsNotVisible(rule,"collector_list")
    }
}

