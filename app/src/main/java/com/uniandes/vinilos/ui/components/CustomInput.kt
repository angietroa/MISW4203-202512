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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag

@Composable
fun CustomInput(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    showError: Boolean = false,
    key: String = ""
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
            onValueChange = onValueChange,
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
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
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
