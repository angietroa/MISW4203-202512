package com.uniandes.vinilos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.clickable
import coil.compose.rememberAsyncImagePainter

@Composable
fun MainAlbum(
    cover: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
) {
    val painter = rememberAsyncImagePainter(model = cover)

    Column(
        modifier = Modifier.clickable { onClick() },
        horizontalAlignment = Alignment.Start
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(114.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Left
        )

        Text(
            text = subtitle,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.tertiary,
            textAlign = TextAlign.Left
        )
    }
}
