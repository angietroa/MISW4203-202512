package com.uniandes.vinilos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BirthdayInput(
    label: String = "Cumpleaños",
    placeholder: String = "DD-MM-YYYY",
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    showError: Boolean = false,
    key: String = "birthday"
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                val digits = newValue.text.filter { it.isDigit() }.take(8)
                val formatted = StringBuilder()
                var cursorPosition = newValue.selection.start
                var dashCount = 0

                for (i in digits.indices) {
                    formatted.append(digits[i])
                    if ((i == 1 || i == 3) && i != digits.lastIndex) {
                        formatted.append("-")
                        if (newValue.selection.start > i + dashCount) {
                            cursorPosition++
                        }
                        dashCount++
                    }
                }

                val finalText = formatted.toString()
                val newCursorPosition = cursorPosition.coerceAtMost(finalText.length)

                onValueChange(
                    TextFieldValue(
                        text = finalText,
                        selection = TextRange(newCursorPosition)
                    )
                )
            },
            placeholder = { Text(placeholder) },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .height(48.dp)
                .testTag("input_${key.lowercase()}"),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.secondary,
                unfocusedTextColor = MaterialTheme.colorScheme.secondary,
                focusedContainerColor = Color.Black,
                unfocusedContainerColor = Color.Black,
                focusedPlaceholderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                cursorColor = MaterialTheme.colorScheme.secondary
            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )

        if (showError) {
            Text(
                text = "*El campo debe completarse",
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp,
                modifier = Modifier.testTag("input_error_${key.lowercase()}")
            )
        }
    }
}
