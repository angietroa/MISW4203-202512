package com.uniandes.vinilos.ui.models

import androidx.compose.ui.text.input.KeyboardType

data class InputField(
    var key: String,
    var label: String,
    var placeholder: String,
    var keyboardType: KeyboardType,
    var value: String
)
