package com.uniandes.vinilos.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Typography
import com.uniandes.vinilos.R

val NunitoFamily = FontFamily(
    Font(R.font.nunito_regular),
    Font(R.font.nunito_bold, weight = FontWeight.Bold),
    Font(R.font.nunito_italic, weight = FontWeight.Normal, style = FontStyle.Italic)
)

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = NunitoFamily,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = NunitoFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
)
